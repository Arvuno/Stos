# Stos

Stos is a cross-platform application (Android/Desktop) built with **Compose Multiplatform** designed to
browse [StackExchange](https://api.stackexchange.com/docs) and [StackOverflow](https://stackoverflow.com/) 

## Why Stos?
Stos was created with the intention to learn Compose Multiplatform. I wanted to write an application that can be quickly launched when you have a problem.

## Features
- Clean and minimal UI focused on readability.
- Cross-platform support (currently Android focus, desktop is planned)
- Search with filters - advanced search to find exactly the answers you need
- Syntax highlighting and formatted code snippets for better readability

## Download
Currently, you can download the latest APK from the [Releases](https://github.com/m4ykey/Stos/releases)
section and install it on your Android device.

## Tech Stack
- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Components:** - [Compose Multiplatform](https://www.jetbrains.com/compose-multiplatform/)
    - [Compottie](https://github.com/alexzhirkevich/compottie) (Lottie animations for Compose Multiplatform)
    - [Multiplatform Markdown Renderer](https://github.com/mikepenz/multiplatform-markdown-renderer) (with Material3 and Coil3 support)
- **Networking:** [Ktor](https://ktor.io/) (with Content Negotiation, Logging, and multi-platform engines like OkHttp, Darwin and CIO)
- **Data Handling:**
    - [Ksoup](https://github.com/MohamedRejeb/Ksoup) (HTML parsing and entities)
    - [AndroidX Paging](https://developer.android.com/jetpack/androidx/releases/paging?hl=en) (Common, Compose, and Runtime)
- **Serialization:** [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) (JSON)
- **Image Loading:** [Coil](https://github.com/coil-kt/coil)

## Inspiration
This application was inspired by [Stack](https://github.com/tylerbwong/stack)

# License
This project is licensed under the **GPL-3.0 license** - see the [LICENSE](https://github.com/m4ykey/Stos/blob/master/LICENSE) file for details.