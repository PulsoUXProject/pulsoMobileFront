package com.pulso.mobile.ui.screens.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.components.PulsoSwitchRow
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.screens.alarm.AlarmChannelSettings
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M15 · Preferencias de accesibilidad — pantalla principal de la pestaña "Ajustes". */
@Composable
fun AccessibilityScreen(
    onNavigate: (PulsoDestination) -> Unit,
    onTestAlert: (AlarmChannelSettings) -> Unit,
) {
    var largeTextEnabled by remember { mutableStateOf(true) }
    var highContrastEnabled by remember { mutableStateOf(false) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }
    var voiceReadoutEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { PulsoTopBar(title = "Ajustes", actionLabel = "Ayuda") },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Settings, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Text(
                text = "Accesibilidad",
                style = MaterialTheme.typography.headlineMedium,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Estos ajustes se aplican a todas las alertas de PULSO. Puedes probarlos antes de guardar.",
                style = MaterialTheme.typography.bodyLarge,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "CÓMO QUIERES QUE TE AVISEMOS",
                style = MaterialTheme.typography.labelLarge,
                color = PulsoTextSecondary,
            )
            PulsoSwitchRow(label = "Texto ampliado", checked = largeTextEnabled, onCheckedChange = { largeTextEnabled = it })
            PulsoSwitchRow(label = "Alto contraste", checked = highContrastEnabled, onCheckedChange = { highContrastEnabled = it })
            PulsoSwitchRow(label = "Vibración", checked = vibrationEnabled, onCheckedChange = { vibrationEnabled = it })
            PulsoSwitchRow(label = "Sonido", checked = soundEnabled, onCheckedChange = { soundEnabled = it })
            PulsoSwitchRow(label = "Lectura por voz", checked = voiceReadoutEnabled, onCheckedChange = { voiceReadoutEnabled = it })

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            PulsoPrimaryButton(
                text = "PROBAR ALERTA",
                onClick = {
                    onTestAlert(
                        AlarmChannelSettings(
                            sound = soundEnabled,
                            vibration = vibrationEnabled,
                            largeText = largeTextEnabled,
                        ),
                    )
                },
            )
        }
    }
}
