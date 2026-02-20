package app.waste2wealth.com.ai

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import app.waste2wealth.com.ui.theme.appBackground
import app.waste2wealth.com.ui.theme.textColor
import app.waste2wealth.com.ui.theme.monteSB
import app.waste2wealth.com.ui.theme.monteBold
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.tensorflow.lite.Interpreter
import java.io.Closeable
import java.io.FileInputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

@Composable
fun AIScreen(
    navController: NavController,
    email: String,
    name: String,
    pfp: String
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // --- UI states ---
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var selectedBitmap by remember { mutableStateOf<Bitmap?>(null) }
    var isPredicting by remember { mutableStateOf(false) }
    var prediction by remember { mutableStateOf<String?>(null) }
    var suggestion by remember { mutableStateOf<String?>(null) }
    var confidence by remember { mutableStateOf<Float?>(null) }

    // --- Image picker ---
    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        imageUri = uri
        prediction = null
        suggestion = null
        confidence = null
        if (uri != null) {
            selectedBitmap = loadBitmapFromUri(context.contentResolver, uri)
        }
    }

    // --- Classifier (kept for the screen's lifetime) ---
    val classifier = remember { WasteClassifier(context, "waste_classifier_model.tflite") }
    DisposableEffect(Unit) { onDispose { classifier.close() } }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(appBackground)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Heading (ChatGPT-like)
        Text(
            text = "Welcome to your AI Assistant",
            color = textColor,
            fontFamily = monteSB,
            fontSize = 22.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Pick Image button (top, green)
        Button(
            onClick = { imagePicker.launch("image/*") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2DBE60))
        ) {
            Text("Pick an Image", color = Color.White, fontFamily = monteBold, fontSize = 16.sp)
        }

        Spacer(Modifier.height(12.dp))

        // Image preview card
        Card(
            elevation = 6.dp,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (imageUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = "Selected image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Text(
                        "No image selected",
                        color = textColor.copy(alpha = 0.7f),
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // Predict button
        Button(
            onClick = {
                val bmp = selectedBitmap ?: return@Button
                isPredicting = true
                prediction = null
                suggestion = null
                confidence = null

                scope.launch {
                    val result = withContext(Dispatchers.Default) {
                        classifier.classify(bmp)
                    }
                    isPredicting = false
                    prediction = result?.label
                    suggestion = result?.suggestion
                    confidence = result?.confidence
                }
            },
            enabled = selectedBitmap != null && !isPredicting,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF2DBE60))
        ) {
            Text(
                if (isPredicting) "Predicting..." else "Predict Waste Type",
                color = Color.White,
                fontFamily = monteBold,
                fontSize = 16.sp
            )
        }

        Spacer(Modifier.height(16.dp))

        // Result card
        if (prediction != null && suggestion != null) {
            Card(
                elevation = 6.dp,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(Modifier.padding(16.dp)) {
                    Text("Prediction:", fontWeight = FontWeight.Bold, color = textColor, fontFamily = monteSB)
                    Text(
                        prediction!! + (confidence?.let { "  (%.1f%%)".format(it * 100f) } ?: ""),
                        color = textColor,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 2.dp, bottom = 8.dp)
                    )
                    Text("Disposal Suggestion:", fontWeight = FontWeight.Bold, color = textColor, fontFamily = monteSB)
                    Text(
                        suggestion!!,
                        color = textColor.copy(alpha = 0.9f),
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}

/* ------------------------- Helpers & Classifier ------------------------- */

private fun loadBitmapFromUri(resolver: ContentResolver, uri: Uri): Bitmap? {
    return resolver.openInputStream(uri)?.use { input ->
        // Decode as ARGB_8888 for TFLite
        BitmapFactory.decodeStream(input)?.copy(Bitmap.Config.ARGB_8888, false)
    }
}

private data class PredictionResult(
    val label: String,
    val confidence: Float,
    val suggestion: String
)

private class WasteClassifier(
    private val context: android.content.Context,
    private val modelAssetName: String
) : Closeable {

    // Adjust if your model expects another size (e.g., 128, 299, etc.)
    private val inputSize = 224
    private val labels = listOf(
        "e-waste",
        "Hazardous",
        "Organic",
        "recyclable",
        "non-recyclable"
    )

    private val displayName = mapOf(
        "e-waste" to "E-waste",
        "Hazardous" to "Hazardous",
        "Organic" to "Organic",
        "recyclable" to "Recyclable",
        "non-recyclable" to "Non-recyclable"
    )

    private val disposalSuggestion = mapOf(
        "e-waste" to "Take to an authorized e-waste collection center. Remove batteries; never burn or toss in household bins.",
        "Hazardous" to "Hazardous waste (chemicals, paints, medical waste, batteries) must go to a specialized facility. Avoid direct contact; follow local safety rules.",
        "Organic" to "Put in the biodegradable/green bin. Compost if possible; keep separate from dry waste.",
        "recyclable" to "Rinse, dry, and place in the dry recyclable bin or give to an authorized MRF/collector. Do not contaminate with food.",
        "non-recyclable" to "Dispose in the general waste bin. Reduce usage and prefer recyclable alternatives."
    )

    private val interpreter: Interpreter by lazy {
        Interpreter(loadModelFile(context, modelAssetName))
    }

    override fun close() {
        try { interpreter.close() } catch (_: Exception) {}
    }

    fun classify(src: Bitmap): PredictionResult? {
        // Preprocess
        val resized = Bitmap.createScaledBitmap(src, inputSize, inputSize, true)
        val inputBuffer = bitmapToFloat32(resized) // [1, H, W, 3]
        val output = Array(1) { FloatArray(labels.size) } // [1, NUM_CLASSES]

        // Inference
        interpreter.run(inputBuffer, output)

        val probs = output[0]
        val (idx, conf) = argMax(probs) ?: return null
        val rawLabel = labels[idx]
        val label = displayName[rawLabel] ?: rawLabel
        val hint = disposalSuggestion[rawLabel] ?: ""

        return PredictionResult(label = label, confidence = conf, suggestion = hint)
    }

    private fun bitmapToFloat32(bmp: Bitmap): ByteBuffer {
        val inputSize3 = inputSize * inputSize * 3
        val buffer = ByteBuffer.allocateDirect(4 * inputSize3).order(ByteOrder.nativeOrder())
        val pixels = IntArray(inputSize * inputSize)
        bmp.getPixels(pixels, 0, inputSize, 0, 0, inputSize, inputSize)

        // Normalize to 0..1 (change if your model expects -1..1 etc.)
        var i = 0
        for (y in 0 until inputSize) {
            for (x in 0 until inputSize) {
                val p = pixels[i++]
                val r = ((p shr 16) and 0xFF) / 255f
                val g = ((p shr 8) and 0xFF) / 255f
                val b = (p and 0xFF) / 255f
                buffer.putFloat(r)
                buffer.putFloat(g)
                buffer.putFloat(b)
            }
        }
        buffer.rewind()
        return buffer
    }

    private fun argMax(arr: FloatArray): Pair<Int, Float>? {
        if (arr.isEmpty()) return null
        var bestIdx = 0
        var best = arr[0]
        for (i in 1 until arr.size) {
            if (arr[i] > best) { best = arr[i]; bestIdx = i }
        }
        return bestIdx to best
    }

    private fun loadModelFile(context: android.content.Context, assetName: String): MappedByteBuffer {
        val afd = context.assets.openFd(assetName)
        FileInputStream(afd.fileDescriptor).use { fis ->
            val channel = fis.channel
            return channel.map(
                FileChannel.MapMode.READ_ONLY,
                afd.startOffset,
                afd.declaredLength
            )
        }
    }
}
