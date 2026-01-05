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

## Project Setup
1. Clone repository and open in the latest version of Android Studio
2. Create ```local.properties``` file
3. Add your [StackExchange](https://api.stackexchange.com/docs) key:
```
k=YOUR_STACK_EXCHANGE_KEY
```

## Screenshots
| ![home.jpg](resources/screenshots/home.jpg) | ![detail.jpg](resources/screenshots/detail.jpg) |
| ![search.jpg](resources/screenshots/search.jpg) | ![tag.jpg](resources/screenshots/tag.jpg) |
| ![user.jpg](resources/screenshots/user.jpg) |

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
```
Copyright (C) 2025 Michał F

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.
```