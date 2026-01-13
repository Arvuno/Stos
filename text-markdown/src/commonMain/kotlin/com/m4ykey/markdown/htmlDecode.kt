package com.m4ykey.markdown

import com.mohamedrejeb.ksoup.entities.KsoupEntities

fun String.normalizeMarkdown() : String {
    val decoded = KsoupEntities.decodeHtml(this)
    val withFixedImages = decoded.fixImageReferences()

    val cleaned = withFixedImages
        .replace("\r\n", "\n")
        .replace("\r", "\n")
        .replace("\u00A0", " ")

    val lines = cleaned.lines()
    val result = StringBuilder()

    for (i in lines.indices) {
        val currentLines = lines[i]
        val isIndentedCode = currentLines.startsWith("    ") || currentLines.startsWith("\t")

        if (isIndentedCode && i > 0) {
            val previousLine = lines[i - 1]
            if (previousLine.trim().isNotEmpty() && !previousLine.startsWith("    ")) {
                result.append("\n")
            }
        }
        result.append(currentLines).append("\n")
    }

    repeat(20) {
        result.append(" ")
    }

    return result.toString()
}

fun String.fixImageReferences(): String {
    val referenceRegex = Regex("""\[(\d+)\]:\s*(\S+)""")
    val references = mutableMapOf<String, String>()

    referenceRegex.findAll(this).forEach { match ->
        val refNum = match.groupValues[1]
        val url = match.groupValues[2]
        references[refNum] = url
    }

    var result = this

    val imageRefRegex = Regex("""\[!\[(.*?)\]\[(\d+)\]\]\[(\d+)\]""")
    result = result.replace(imageRefRegex) { match ->
        val alt = match.groupValues[1]
        val refNum = match.groupValues[2]
        val url = references[refNum] ?: ""
        "![$alt]($url)"
    }

    val simpleImageRefRegex = Regex("""\[!\[(.*?)\]\[(\d+)\]\]""")
    result = result.replace(simpleImageRefRegex) { match ->
        val alt = match.groupValues[1]
        val refNum = match.groupValues[2]
        val url = references[refNum] ?: ""
        "![$alt]($url)"
    }

    return result
}