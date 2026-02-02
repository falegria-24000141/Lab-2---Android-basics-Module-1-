package com.fernandoalegria.trafficlightsimulator02

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.fernandoalegria.trafficlightsimulator02.ui.theme.TrafficLightSimulator02Theme
import kotlinx.coroutines.delay

// Estados posibles del semáforo
enum class LightState { Red, Yellow, Green }

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Habilita diseño de pantalla completa
        setContent {
            TrafficLightSimulator02Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    // Llamada a la pantalla principal con padding del sistema
                    TrafficLightScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun TrafficLightScreen(modifier: Modifier = Modifier) {
    // Variable de estado que recuerda qué luz está encendida
    var currentLight by remember { mutableStateOf(LightState.Red) }

    // LaunchedEffect inicia una corrutina al cargar la pantalla
    LaunchedEffect(Unit) {
        while (true) { // Bucle infinito para que el semáforo nunca se detenga
            currentLight = LightState.Red
            delay(2000) // Espera 2 segundos en rojo

            currentLight = LightState.Green
            delay(2000) // Espera 2 segundos en verde

            currentLight = LightState.Yellow
            delay(1000) // Espera 1 segundo en amarillo
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Caja negra que contiene las luces
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(Color.Black)
                .padding(20.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Dibujamos cada círculo indicando si debe estar encendido
                LightCircle(color = Color.Red, isActive = currentLight == LightState.Red)
                LightCircle(color = Color.Yellow, isActive = currentLight == LightState.Yellow)
                LightCircle(color = Color.Green, isActive = currentLight == LightState.Green)
            }
        }
    }
}

@Composable
fun LightCircle(color: Color, isActive: Boolean) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .clip(CircleShape) // Corta el Box en forma de círculo
            // Si isActive es true usa el color, si no usa un gris apagado
            .background(if (isActive) color else Color.DarkGray)
    )
}