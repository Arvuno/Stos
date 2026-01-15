package com.m4ykey.markdown

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.material3.LocalContentColor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.model.MarkdownTypography
import dev.snipme.highlights.Highlights
import dev.snipme.highlights.model.SyntaxThemes

@Composable
fun TextMarkdown(
    text : String,
    modifier : Modifier = Modifier,
    fontSize : TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color : Color = LocalContentColor.current,
    textAlign: TextAlign = TextAlign.Start,
    alignment: Alignment = Alignment.Center
) {
    val isDarkTheme = isSystemInDarkTheme()

    val processedText = remember(text) {
        text
            .decodeAndCleanHtml()
            .fixImageReferences()
            .normalizeMarkdown()
    }

    val highlightBuilder = remember(isDarkTheme) {
        Highlights.Builder().theme(SyntaxThemes.atom(darkMode = isDarkTheme))
    }

    val customComponents = remember(highlightBuilder) {
        markdownComponents(
            codeBlock = {
                MarkdownHighlightedCodeBlock(
                    content = it.content,
                    node = it.node,
                    highlightsBuilder = highlightBuilder
                )
            },
            codeFence = {
                MarkdownHighlightedCodeFence(
                    content = it.content,
                    node = it.node,
                    highlightsBuilder = highlightBuilder
                )
            }
        )
    }

    val linkColor = Color(0xFF1E88E5)
    val customTypography = remember(fontSize, fontWeight, color, textAlign) {
        createMarkdownTypography(fontSize, fontWeight, color, textAlign, linkColor)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        SelectionContainer {
            Markdown(
                content = processedText,
                modifier = Modifier.wrapContentHeight(),
                imageTransformer = coil3ImageTransfer,
                typography = customTypography,
                components = customComponents
            )
        }
    }
}

private fun createMarkdownTypography(
    fontSize : TextUnit,
    fontWeight : FontWeight,
    color : Color,
    textAlign : TextAlign,
    linkColor : Color
) : MarkdownTypography {
    val baseParagraph = TextStyle(
        fontSize = fontSize,
        fontWeight = fontWeight,
        color = color,
        textAlign = textAlign
    )

    val linkSpanStyle = SpanStyle(
        color = linkColor,
        textDecoration = TextDecoration.Underline
    )
    val code = TextStyle(
        fontSize = fontSize * 0.85f,
        fontFamily = FontFamily.Monospace,
        lineHeight = fontSize * 1.2f
    )

    return object : MarkdownTypography {
        override val h1 = TextStyle(fontSize = fontSize * 1.6f, fontWeight = fontWeight)
        override val h2 = TextStyle(fontSize = fontSize * 1.4f, fontWeight = fontWeight)
        override val h3 = TextStyle(fontSize = fontSize * 1.2f, fontWeight = fontWeight)
        override val h4 = TextStyle(fontSize = fontSize * 1.1f, fontWeight = fontWeight)
        override val h5 = TextStyle(fontSize = fontSize, fontWeight = fontWeight)
        override val h6 = TextStyle(fontSize = fontSize * 0.9f, fontWeight = fontWeight)
        override val paragraph = baseParagraph
        override val text = paragraph
        override val bullet = paragraph
        override val list = paragraph
        override val ordered = paragraph
        override val quote = TextStyle(fontSize = fontSize, fontStyle = FontStyle.Italic)
        override val code = code
        override val inlineCode = code
        override val table = paragraph
        override val textLink = TextLinkStyles(style = linkSpanStyle)
    }
}