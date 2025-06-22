package com.example.darkmode.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun ButtonSwitch(
    isDarkMode: () -> Unit,
    isLightMode: () -> Unit,
    isDarkModeActive: Boolean,
) {

    var selectedOption by remember { mutableStateOf(if (isDarkModeActive) "dark" else "light") }

    LaunchedEffect(isDarkModeActive) {
        selectedOption = if (isDarkModeActive) "dark" else "light"
    }

    val switchBgColor = MaterialTheme.colorScheme.surfaceContainerHigh
    val sliderColorChecked = MaterialTheme.colorScheme.surface
    val sliderColorUnchecked = MaterialTheme.colorScheme.surface
    val textColor = MaterialTheme.colorScheme.onBackground

    val animatedSliderColor by animateColorAsState(
        targetValue = if (selectedOption == "dark") sliderColorChecked else sliderColorUnchecked,
        animationSpec = tween(durationMillis = 300)
    )

    val switchPadding = 4.dp
    val labelPaddingHorizontal = 16.dp
    val labelPaddingVertical = 8.dp

    Card(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .padding(horizontal = 30.dp),
        shape = RoundedCornerShape(9999.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(containerColor = switchBgColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(switchPadding)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.5f)
                    .align(if (selectedOption == "light") Alignment.CenterStart else Alignment.CenterEnd)
                    .clip(RoundedCornerShape(9999.dp))
                    .background(animatedSliderColor)
                    .animateContentSize(animationSpec = tween(durationMillis = 300))
            )


            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // "Light" mode clickable area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable {
                            if (selectedOption != "light") {
                                selectedOption = "light"
                                isLightMode()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Light",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (selectedOption == "light") FontWeight.Bold else FontWeight.Normal,
                        color = textColor.copy(alpha = if (selectedOption == "light") 1f else 0.7f),
                        modifier = Modifier.padding(horizontal = labelPaddingHorizontal, vertical = labelPaddingVertical)
                    )
                }

                // "Dark" mode clickable area
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clickable {
                            if (selectedOption != "dark") {
                                selectedOption = "dark"
                                isDarkMode()
                            }
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Dark",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = if (selectedOption == "dark") FontWeight.Bold else FontWeight.Normal,
                        color = textColor.copy(alpha = if (selectedOption == "dark") 1f else 0.7f),
                        modifier = Modifier.padding(horizontal = labelPaddingHorizontal, vertical = labelPaddingVertical)
                    )
                }
            }
        }
    }
}