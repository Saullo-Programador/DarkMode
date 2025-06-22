package com.example.darkmode.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.darkmode.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(
    showBottomSheet: ()-> Unit = {},
    isDarkMode: () -> Unit,
    isLightMode: () -> Unit,
    isDarkModeActive: Boolean,
){
    val sheetState = rememberModalBottomSheetState()

    val icon = if (isDarkModeActive){
        painterResource(R.drawable.moon)
    } else {
        painterResource(R.drawable.sun)
    }

    val rotationAngle by animateFloatAsState(
        targetValue = if (isDarkModeActive) 360f else 0f,
        animationSpec = tween(
            durationMillis = 800,
            delayMillis = 0,

        ),
        label = "Image Rotation"
    )



    ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.background,
        onDismissRequest = {
            showBottomSheet()
        },
        sheetState = sheetState
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Box(
                modifier = Modifier
                    .padding(start= 60.dp, end = 60.dp ,bottom = 20.dp)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = icon,
                    contentDescription = if(isDarkModeActive)"Modo Escuro" else "Modo Claro",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(200.dp)
                        .graphicsLayer(rotationZ = rotationAngle)
                )
            }

            ButtonSwitch(
                isDarkMode = isDarkMode,
                isLightMode = isLightMode,
                isDarkModeActive = isDarkModeActive
            )
        }
    }
}