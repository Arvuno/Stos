package com.m4ykey.markdown

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImagePainter
import coil3.compose.LocalPlatformContext
import coil3.compose.rememberAsyncImagePainter
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mikepenz.markdown.compose.LazyMarkdownSuccess
import com.mikepenz.markdown.compose.components.markdownComponents
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeBlock
import com.mikepenz.markdown.compose.elements.MarkdownHighlightedCodeFence
import com.mikepenz.markdown.compose.extendedspans.ExtendedSpans
import com.mikepenz.markdown.compose.extendedspans.RoundedCornerSpanPainter
import com.mikepenz.markdown.compose.extendedspans.SquigglyUnderlineSpanPainter
import com.mikepenz.markdown.compose.extendedspans.rememberSquigglyUnderlineAnimator
import com.mikepenz.markdown.m3.Markdown
import com.mikepenz.markdown.model.ImageData
import com.mikepenz.markdown.model.ImageTransformer
import com.mikepenz.markdown.model.MarkdownTypography
import com.mikepenz.markdown.model.markdownExtendedSpans
import com.mikepenz.markdown.model.rememberMarkdownState
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

    val markdownContent = remember(text) {
        val normalizedEndings = text.replace("\r\n", "\n")
        val processed = normalizedEndings.normalizeMarkdown()
        if (processed.isBlank()) "" else "$processed\n"
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

        object : MarkdownTypography {
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

    Box(
        modifier = modifier.fillMaxWidth(),
        contentAlignment = alignment
    ) {
        key(markdownContent) {
            Markdown(
                modifier = Modifier.wrapContentHeight(),
                imageTransformer = coil3ImageTransfer,
                typography = customTypography,
                components = customComponents,
                content = markdownContent
            )
        }
    }
}