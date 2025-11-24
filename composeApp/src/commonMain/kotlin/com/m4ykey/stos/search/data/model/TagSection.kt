package com.m4ykey.stos.search.data.model

import org.jetbrains.compose.resources.StringResource

data class TagSection(
    val title : StringResource,
    val tags : List<String>
)

val mobileTags = listOf(
    "android-studio", "android-jetpack-compose", "xcode", "react-native", "flutter", "material-ui"
)
val databaseTags = listOf(
    "sql", "mysql", "postgresql", "mongodb", "sqlite", "oracle"
)
val testTags = listOf(
    "junit", "selenium", "cypress", "github-actions"
)
val cloudTags = listOf(
    "docker", "kubernetes", "aws", "azure", "firebase", "jenkins", "terraform"
)
val frameworksTags = listOf(
    "angular", "vue.js", "spring", "flask", "django", "express", "laravel", "bootstrap", "tensorflow", "pandas", "numpy"
)
val languageTags = listOf(
    "typescript", "c++", "swift", "ruby", "go", "kotlin", "r", "rust", "scala", "dart", "bash", "objective-c", "c"
)