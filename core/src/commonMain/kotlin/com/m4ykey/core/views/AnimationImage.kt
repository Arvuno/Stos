package com.m4ykey.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kmp_stos.core.generated.resources.Res

@Composable
fun AnimationImage(
    jsonPath : String,
    modifier : Modifier = Modifier
) {

    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes(jsonPath).decodeToString()
        )
    }

    BoxWithConstraints(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        val size = if (maxWidth > 600.dp) 150.dp else 250.dp

        val painter = rememberLottiePainter(
            composition = composition,
            iterations = Compottie.IterateForever
        )

        Image(
            contentDescription = "Lottie animation",
            modifier = Modifier.size(size),
            painter = painter,
            contentScale = ContentScale.Fit
        )
    }
}