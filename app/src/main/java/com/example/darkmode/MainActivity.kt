package com.example.darkmode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.darkmode.ui.theme.DarkModeTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.darkmode.ui.components.BottomSheet
import com.example.darkmode.ui.viewModel.DataStoreViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val userViewModel: DataStoreViewModel = hiltViewModel()
            val isDarkMode by userViewModel.isDarkMode.collectAsState( initial = false )
            var showBottomSheet by remember { mutableStateOf(false) }
            DarkModeTheme (
                dynamicColor = false,
                darkTheme = isDarkMode
            ){
                Surface(
                    modifier = Modifier
                        .windowInsetsPadding(WindowInsets.statusBars)
                        .fillMaxSize(),
                ){
                    AppScreen(
                        isDarkMode = isDarkMode,
                        onDarkModeChange = { userViewModel.setDarkMode(it) },
                        showBottomSheet = showBottomSheet,
                        onToggleBottomSheet = { showBottomSheet = !showBottomSheet }
                    )
                }
            }
        }
    }
}

@Composable
fun AppScreen(
    isDarkMode: Boolean,
    showBottomSheet: Boolean,
    onToggleBottomSheet: () -> Unit,
    onDarkModeChange: (Boolean) -> Unit
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Aparência"
            )
            TextButton(
                onClick = onToggleBottomSheet
            ) {
                Text(
                    color = Color.Blue,
                    text = if (isDarkMode) "Modo Escuro" else "Modo Claro"
                )
            }
        }
        if(showBottomSheet){
            BottomSheet(
                showBottomSheet = onToggleBottomSheet,
                isDarkMode = { onDarkModeChange(true) },
                isLightMode = { onDarkModeChange(false) },
                isDarkModeActive = isDarkMode
            )
        }
    }
}


@Preview(
    name = "Light Mode Preview with BottomSheet",
    showBackground = true
)
@Composable
fun LightModePreview() {
    DarkModeTheme(darkTheme = false) {
        Surface {
            PreviewAppScreen(isDarkMode = false)
        }
    }
}

@Preview(
    name = "Dark Mode Preview with BottomSheet",
    showBackground = true
)
@Composable
fun DarkModePreview() {
    DarkModeTheme(darkTheme = true) {
        Surface {
            PreviewAppScreen(isDarkMode = true)
        }
    }
}

// Versão isolada para pré-visualização, com BottomSheet sempre visível
@Composable
fun PreviewAppScreen(isDarkMode: Boolean) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Aparência"
            )
            TextButton(
                onClick = { }
            ) {
                Text(
                    color = Color.Blue,
                    text = if (isDarkMode) "Modo Escuro" else "Modo Claro"
                )
            }
        }

        // Força a visualização do BottomSheet na preview
        BottomSheet(
            showBottomSheet = {},
            isDarkMode = {},
            isLightMode = {},
            isDarkModeActive = isDarkMode
        )
    }
}
