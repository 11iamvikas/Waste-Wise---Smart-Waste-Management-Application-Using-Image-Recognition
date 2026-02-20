package app.waste2wealth.com

import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.RECEIVER_EXPORTED
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.rememberScaffoldState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModelProvider
import app.waste2wealth.com.location.LocationViewModel
import app.waste2wealth.com.login.onboarding.SmsBroadcastReceiver
import app.waste2wealth.com.login.onboarding.SmsBroadcastReceiver.SmsBroadcastReceiverListener
import app.waste2wealth.com.navigation.NavigationController
import app.waste2wealth.com.ui.theme.Waste2WealthTheme
import app.waste2wealth.com.ui.theme.appBackground
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.phone.SmsRetriever
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var smsBroadcastReceiver: SmsBroadcastReceiver
    private lateinit var viewModel: LocationViewModel

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Waste2WealthTheme {
                val systemUiController = rememberSystemUiController()
                systemUiController.setSystemBarsColor(appBackground)
                systemUiController.setNavigationBarColor(appBackground)
                val navController = rememberAnimatedNavController()
                viewModel = ViewModelProvider(this)[LocationViewModel::class.java]

                val locationViewModel: LocationViewModel = hiltViewModel()
                val scaffoldState = rememberScaffoldState()
                val client = SmsRetriever.getClient(this)
                client.startSmsUserConsent(null)
                NavigationController(scaffoldState, locationViewModel, navController)
            }
        }
    }

    private fun registerBroadcastReceiver() {
        smsBroadcastReceiver = SmsBroadcastReceiver()
        smsBroadcastReceiver.smsBroadcastReceiverListener = object : SmsBroadcastReceiverListener {
            override fun onSuccess(intent: Intent?) {
                intent?.let { startActivityForResult(it, 200) }
            }

            override fun onFailure() {}
        }

        val intentFilter = IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION)

        // Android 14+ requires explicit receiver export flag
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            registerReceiver(
                smsBroadcastReceiver,
                intentFilter,
                RECEIVER_EXPORTED  // Use RECEIVER_EXPORTED for system broadcasts
            )
        } else {
            // Move suppression to the function level
            registerReceiverLegacy(smsBroadcastReceiver, intentFilter)
        }
    }

    // Add this function to contain the legacy registration
    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    private fun registerReceiverLegacy(
        receiver: SmsBroadcastReceiver,
        filter: IntentFilter
    ) {
        registerReceiver(receiver, filter)
    }

    override fun onStart() {
        super.onStart()
        registerBroadcastReceiver()
    }

    override fun onStop() {
        super.onStop()
        try {
            unregisterReceiver(smsBroadcastReceiver)
        } catch (e: IllegalArgumentException) {
            // Receiver not registered, safe to ignore
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200) {
            if (resultCode == RESULT_OK && data != null) {
                val message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE)
                println("Message $message")
                viewModel.result.value = message
            }
        }
    }
}