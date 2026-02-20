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
- [Authors](#-authors)

---

## 📖 Overview
Rapid urbanization has significantly increased global waste. Traditional systems rely on manual processes and lack real-time reporting. **WasteWise** addresses these limitations by providing a **mobile-first, AI-driven platform** that:

* **Detects** waste type using on-device image recognition.
* **Suggests** proper disposal methods based on classification.
* **Tracks** waste locations via GPS for collection.
* **Rewards** users to encourage consistent participation.

---

## 🚀 Features

* 📸 **Image-Based Waste Detection** – Classifies waste into: Organic, Recyclable, Non-Recyclable, Hazardous, and E-Waste.
* 📍 **Real-Time Location Reporting** – Pins waste spots on a map for authorities.
* 🎁 **Reward System** – Gamified experience where users earn points for reporting.
* ☁ **Cloud Integration** – Seamless data sync via Firebase.
* 📱 **Edge AI** – Lightweight model runs locally on the phone for instant results.

---

## 🏗 System Architecture

The system follows a **three-layer architecture** to ensure low latency and high scalability:



1.  **Client Layer (Mobile App):** Built with Kotlin & Jetpack Compose for UI/UX.
2.  **AI Layer (TensorFlow Lite):** Handles on-device inference for low latency.
3.  **Backend Layer (Firebase):** Manages Auth, Firestore database, and Cloud Storage.

---

## 🛠 Tech Stack

| Category | Technology |
| :--- | :--- |
| **Mobile** | Kotlin, Jetpack Compose, CameraX, MVVM |
| **Backend** | Firebase Auth, Cloud Firestore, Firebase Storage |
| **Machine Learning** | Python, TensorFlow, Keras, MobileNetV2 |
| **Tools** | Android Studio, Git |

---

## 🤖 Machine Learning Model

The core of WasteWise is a **Convolutional Neural Network (CNN)** optimized for mobile devices.

* **Base Architecture:** MobileNetV2 (Transfer Learning)
* **Input Size:** $224 \times 224$ pixels
* **Optimizer:** Adam
* **Loss Function:** Categorical Crossentropy

**Why MobileNetV2?**
It provides an excellent trade-off between **latency** and **accuracy**, making it ideal for real-time mobile classification without draining the battery.

---

## ⚙ Installation

### 1️⃣ Clone Repository
```bash
git clone [https://github.com/11iamvikas/Waste-Wise---Smart-Waste-Management-Application-Using-Image-Recognition.git](https://github.com/11iamvikas/Waste-Wise---Smart-Waste-Management-Application-Using-Image-Recognition.git)
cd Waste-Wise---Smart-Waste-Management-Application-Using-Image-Recognition
---
### 2️⃣ Firebase Configuration
To connect the app to the backend, follow these steps:
1.  Go to the [Firebase Console](https://console.firebase.google.com/).
2.  **Create a New Project** (e.g., "WasteWise-App").
3.  **Add an Android App:** Use your project's package name (found in `build.gradle`).
4.  **Download `google-services.json`** and move it into the `/app` directory of this project.
5.  **Enable Services:** * **Authentication:** Enable Email/Google provider.
    * **Firestore Database:** Start in test mode (or configure security rules).
    * **Cloud Storage:** For storing waste images for reporting.

### 3️⃣ Build & Run
1.  Launch **Android Studio**.
2.  Select **Open** and navigate to the cloned repository.
3.  Wait for the project to load, then click **Sync Project with Gradle Files** (elephant icon).
4.  Connect a physical Android device or start an **AVD (Emulator)**.
5.  Click the **Run** button (green play icon ▶) in the top toolbar.

---

## ▶ Usage

Once the app is running, follow these steps to manage waste:

1.  **Capture:** Open the in-app camera and snap a clear photo of the waste item.
2.  **Analyze:** The on-device AI model processes the image to predict the category (Organic, Plastic, etc.) and shows a **confidence score**.
3.  **Dispose:** The app provides a disposal suggestion or directs you to the nearest appropriate bin.
4.  **Earn:** After disposal, confirm the action to update your **Reward Points** profile.

---

## 🔮 Future Scope

- [ ] **Municipal Integration:** Connecting with city garbage collection APIs for real-time pickup requests.
- [ ] **Expanded Dataset:** Adding support for 10+ additional sub-categories (e.g., medical waste, textiles).
- [ ] **Multilingual Support:** Localization for global accessibility.
- [ ] **Offline Mode:** Using Room database for local caching when internet is unavailable.

---

## 🤝 Contributing

Contributions are what make the open-source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1.  **Fork** the Project.
2.  Create your **Feature Branch** (`git checkout -b feature/AmazingFeature`).
3.  **Commit** your Changes (`git commit -m 'Add some AmazingFeature'`).
4.  **Push** to the Branch (`git push origin feature/AmazingFeature`).
5.  Open a **Pull Request**.

---
