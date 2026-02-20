# ♻️ WasteWise – Smart Waste Management Application

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9+-blue.svg)](https://kotlinlang.org/)
[![TensorFlow Lite](https://img.shields.io/badge/ML-TensorFlow%20Lite-orange.svg)](https://www.tensorflow.org/lite)
[![Firebase](https://img.shields.io/badge/Backend-Firebase-ffca28?logo=firebase)](https://firebase.google.com/)

WasteWise is an AI-powered smart waste management system that combines **mobile technology, image recognition, geolocation, and incentive-based engagement** to promote proper waste segregation and responsible disposal practices.

It enables users to report waste, classify it automatically using deep learning, and receive rewards for environmentally responsible actions.

---

## 📌 Table of Contents
- [Overview](#-overview)
- [Features](#-features)
- [System Architecture](#-system-architecture)
- [Tech Stack](#-tech-stack)
- [Machine Learning Model](#-machine-learning-model)
- [Installation](#-installation)
- [Usage](#-usage)
- [Future Scope](#-future-scope)
- [Contributing](#-contributing)

---

## 📖 Overview
Rapid urbanization has significantly increased global waste. Traditional systems rely on manual processes and lack real-time reporting. **WasteWise** addresses these limitations by providing a **mobile-first, AI-driven platform** that:

- **Detects** waste type using on-device image recognition.
- **Suggests** proper disposal methods based on classification.
- **Tracks** waste locations via GPS for collection.
- **Rewards** users to encourage consistent participation.

---

## 🚀 Features

- 📸 **Image-Based Waste Detection** – Classifies waste into: Organic, Recyclable, Non-Recyclable, Hazardous, and E-Waste.
- 📍 **Real-Time Location Reporting** – Pins waste spots on a map for authorities.
- 🎁 **Reward System** – Gamified experience where users earn points for reporting.
- ☁ **Cloud Integration** – Seamless data sync via Firebase.
- 📱 **Edge AI** – Lightweight model runs locally on the phone for instant results.

---

## 🏗 System Architecture

The system follows a **three-layer architecture** to ensure low latency and high scalability:

1. **Client Layer (Mobile App):** Built with Kotlin & Jetpack Compose for UI/UX.  
2. **AI Layer (TensorFlow Lite):** Handles on-device inference for low latency.  
3. **Backend Layer (Firebase):** Manages Auth, Firestore database, and Cloud Storage.

---

## 🛠 Tech Stack

| Category | Technology |
|--------|-------------|
| **Mobile** | Kotlin, Jetpack Compose, CameraX, MVVM |
| **Backend** | Firebase Auth, Cloud Firestore, Firebase Storage |
| **Machine Learning** | Python, TensorFlow, Keras, MobileNetV2 |
| **Tools** | Android Studio, Git |

---

## 🤖 Machine Learning Model

The core of WasteWise is a **Convolutional Neural Network (CNN)** optimized for mobile devices.

- **Base Architecture:** MobileNetV2 (Transfer Learning)  
- **Input Size:** 224 × 224 pixels  
- **Optimizer:** Adam  
- **Loss Function:** Categorical Crossentropy  

**Why MobileNetV2?**  
It provides an excellent trade-off between **latency** and **accuracy**, making it ideal for real-time mobile classification without draining the battery.

---

## ⚙ Installation

### 1️⃣ Clone Repository
```bash
git clone https://github.com/11iamvikas/Waste-Wise---Smart-Waste-Management-Application-Using-Image-Recognition.git
cd Waste-Wise---Smart-Waste-Management-Application-Using-Image-Recognition
```

---

### 2️⃣ Firebase Configuration

To connect the app to the backend:

1. Go to Firebase Console.
2. **Create a New Project** (e.g., `WasteWise-App`).
3. **Add Android App** → Enter package name from `build.gradle`.
4. Download `google-services.json`.
5. Place it inside:
```
/app
```

6. Enable services:
   - Authentication → Email/Google
   - Firestore Database → Test Mode (or rules)
   - Cloud Storage → For image uploads

---

### 3️⃣ Build & Run

1. Open **Android Studio**
2. Click **Open Project**
3. Sync Gradle
4. Connect device or start emulator
5. Click **Run ▶**

---

## ▶ Usage

1. **Capture** – Take a photo of waste.
2. **Analyze** – AI predicts category + confidence.
3. **Dispose** – App suggests proper bin.
4. **Earn** – Confirm disposal and get points.

---

## 🔮 Future Scope

- Municipal API integration for pickup requests
- Expanded dataset (medical waste, textiles, etc.)
- Multilingual support
- Offline mode with Room database

---

## 🤝 Contributing

Contributions are welcome and appreciated!

Steps:

1. Fork repo
2. Create branch  
```
git checkout -b feature/AmazingFeature
```
3. Commit  
```
git commit -m "Add AmazingFeature"
```
4. Push  
```
git push origin feature/AmazingFeature
```
5. Open Pull Request

---

### ⭐ If you like this project, consider giving it a star!
