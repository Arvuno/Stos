package com.m4ykey.stos.core.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.alexzhirkevich.compottie.Compottie
import io.github.alexzhirkevich.compottie.LottieCompositionSpec
import io.github.alexzhirkevich.compottie.rememberLottieComposition
import io.github.alexzhirkevich.compottie.rememberLottiePainter
import kmp_stos.composeapp.generated.resources.Res

@Composable
fun AnimationImage() {
    val composition by rememberLottieComposition {
        LottieCompositionSpec.JsonString(
            Res.readBytes("files/black_cat_animation.json").decodeToString()
        )
    }

    BoxWithConstraints {
        val maxWidthDp = maxWidth
        var size : Dp = 250.dp
        if (maxWidthDp > 600.dp) {
            size = 150.dp
        }

        Image(
            contentDescription = "Lottie animation",
            painter = rememberLottiePainter(
                composition = composition,
                iterations = Compottie.IterateForever
            ),
            modifier = Modifier.size(size)
        )
    }
}