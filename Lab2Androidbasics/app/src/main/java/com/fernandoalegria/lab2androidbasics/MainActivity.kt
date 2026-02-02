package com.fernandoalegria.lab2androidbasics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.fernandoalegria.lab2androidbasics.ui.theme.Lab2AndroidbasicsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab2AndroidbasicsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CharacterSheet()
                }
            }
        }
    }
}

@Composable
fun CharacterSheet() {
    // Estados para los valores
    var vit by remember { mutableIntStateOf(0) }
    var dex by remember { mutableIntStateOf(0) }
    var wis by remember { mutableIntStateOf(0) }

    // Estados para saber si ya se hizo el roll (Lo nuevo que pediste)
    var vitRolled by remember { mutableStateOf(false) }
    var dexRolled by remember { mutableStateOf(false) }
    var wisRolled by remember { mutableStateOf(false) }

    val total = vit + dex + wis
    val allRolled = vitRolled && dexRolled && wisRolled

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "RPG Character Sheet",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Stat Rows con la propiedad 'enabled' controlada por el estado
        StatRow("Vitality", vit, enabled = !vitRolled) {
            vit = (3..18).random()
            vitRolled = true
        }
        StatRow("Dexterity", dex, enabled = !dexRolled) {
            dex = (3..18).random()
            dexRolled = true
        }
        StatRow("Wisdom", wis, enabled = !wisRolled) {
            wis = (3..18).random()
            wisRolled = true
        }

        Spacer(modifier = Modifier.weight(1f))

        // Mostrar el Total y Feedback solo si se ha empezado a tirar
        if (vitRolled || dexRolled || wisRolled) {
            Text(text = "Total Score: $total", style = MaterialTheme.typography.displaySmall)

            when {
                total < 30 -> Text("Re-roll recommended!", color = Color.Red, fontWeight = FontWeight.Bold)
                total >= 50 -> Text("Godlike! ⚔️", color = Color(0xFFFFD700), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.ExtraBold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botón de Reiniciar: Solo aparece cuando los 3 botones han sido presionados
        if (allRolled) {
            Button(
                onClick = {
                    vit = 0; dex = 0; wis = 0
                    vitRolled = false; dexRolled = false; wisRolled = false
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("NUEVA PARTIDA / RESET")
            }
        }
    }
}

@Composable
fun StatRow(name: String, value: Int, enabled: Boolean, onRoll: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp).fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = name.uppercase(), style = MaterialTheme.typography.labelMedium)
                Text(text = if (value == 0) "-" else value.toString(), style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            }
            // El botón se deshabilita automáticamente gracias al parámetro 'enabled'
            Button(onClick = onRoll, enabled = enabled) {
                Text("Roll")
            }
        }
    }
}