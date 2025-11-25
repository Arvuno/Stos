package com.m4ykey.stos.search.data.model

import org.jetbrains.compose.resources.StringResource

data class TagSection(
    val title : StringResource,
    val tags : List<String>
)

val mobileTags = listOf(
    "android-studio", "android-jetpack-compose", "xcode", "react-native", "flutter", "material-ui",
    "ios-swiftui", "android-kotlin", "kotlin-multiplatform", "jetpack-compose-navigation", "rxjava", "coroutines"
)
val databaseTags = listOf(
    "sql", "mysql", "postgresql", "mongodb", "sqlite", "oracle", "redis", "cassandra", "realm", "jpa",
    "elasticsearch", "hibernate"
)
val testTags = listOf(
    "junit", "selenium", "cypress", "github-actions", "macha", "pytest", "load-testing", "test-automation"
)
val cloudTags = listOf(
    "docker", "kubernetes", "aws", "azure", "firebase", "jenkins", "terraform", "lambda", "serverless",
    "google-cloud-platform",
)
val frameworksTags = listOf(
    "angular", "vue.js", "spring", "flask", "django", "express", "laravel", "bootstrap", "tensorflow", "pandas", "numpy",
    "reactjs", "next.js", "redux", "graphql", "fastapi", "express.js"
)
val languageTags = listOf(
    "typescript", "c++", "swift", "ruby", "go", "kotlin", "r", "rust", "scala", "dart", "bash", "objective-c", "c"
)

val webTags = listOf(
    "html", "css", "javascript", "webpack", "babel", "sass"
)

val aiTags = listOf(
    "ai", "machine-learning", "deep-learning", "pytorch", "data-science"
)