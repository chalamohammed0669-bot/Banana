# Banana - Modern Short-Video Social Media Platform

Banana is a modern short-video social media platform built with Kotlin and Jetpack Compose, combining the best features of TikTok, Instagram Reels, Messenger, and Snapchat.

## Features

- 🎥 **Video System** - Record, upload, edit videos with filters, effects, and music
- 👥 **Social System** - Follow users, like, comment, share, and save videos
- 💬 **Messaging System** - One-to-one and group chat with media sharing
- 📞 **Calling System** - Audio and video calls with call history
- 🔴 **Live Streaming** - Stream live content with viewer interactions
- 🔔 **Notifications** - Real-time notifications for followers, likes, comments, and more
- 🔐 **Authentication** - Email, Google Sign-In, and password reset

## Tech Stack

### Frontend
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose with Material 3
- **Architecture**: MVVM + Repository Pattern + Clean Architecture
- **Media**: ExoPlayer, CameraX
- **Calls**: WebRTC

### Backend
- **Authentication**: Firebase Authentication
- **Database**: Firebase Firestore
- **Storage**: Firebase Storage
- **Messaging**: Firebase Cloud Messaging
- **Analytics**: Firebase Analytics

## Project Structure

```
Banana/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/banana/
│   │   │   │   ├── ui/
│   │   │   │   ├── data/
│   │   │   │   ├── domain/
│   │   │   │   └── di/
│   │   │   ├── res/
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle
│   └── google-services.json (add your Firebase config)
├── build.gradle
├── settings.gradle
└── README.md
```

## Feature Modules

- **Auth System** - User authentication and account management
- **Video System** - Video recording, uploading, editing
- **Social System** - Follow, like, comment, share functionality
- **Messaging System** - Chat and messaging features
- **Calling System** - Audio/video calling
- **Live Streaming System** - Live broadcast capabilities
- **Notification System** - Real-time notifications

## Getting Started

### Prerequisites
- Android Studio Hedgehog or later
- Android SDK 24+
- Kotlin 1.8+
- Firebase project

### Installation

1. Clone the repository
```bash
git clone https://github.com/chalamohammed0669-bot/Banana.git
cd Banana
```

2. Configure Firebase
   - Create a Firebase project
   - Download `google-services.json`
   - Place it in `app/` directory

3. Open in Android Studio
   - File → Open → Select project
   - Gradle sync will auto-run

4. Build and Run
   - Click Run or press Shift+F10

## Firebase Setup

### Collections in Firestore
- `users` - User profiles and authentication data
- `videos` - Video metadata and content
- `comments` - Video comments
- `likes` - Like records
- `followers` - Follower relationships
- `messages` - Direct and group messages
- `notifications` - User notifications
- `calls` - Call history
- `stories` - Story content
- `reports` - User reports
- `livestreams` - Live stream data

## Architecture

We follow Clean Architecture with MVVM pattern:

```
Presentation Layer (UI)
        ↓
ViewModel Layer
        ↓
Repository Layer
        ↓
Data Layer (Firebase/Local)
```

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md) for guidelines.

## License

MIT License
