package com.pulso.mobile.ui.screens.alarm

import android.content.Intent
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.components.PulsoSwitchRow
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoSurface
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary
import com.pulso.mobile.ui.theme.PulsoWarningAccent
import com.pulso.mobile.ui.theme.PulsoWarningBorder

/** M05 · Permisos y canal — se abre desde "ACTIVAR ALARMA" en [com.pulso.mobile.ui.screens.today.DetailScreen]. */
@Composable
fun AlarmChannelScreen(
    onNavigate: (PulsoDestination) -> Unit,
    onBack: () -> Unit,
    onActivated: (AlarmChannelSettings) -> Unit,
    onOpenStatus: () -> Unit,
) {
    val context = LocalContext.current
    var showNotificationsWarning by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }
    var largeTextEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = { PulsoTopBar(title = "Alarma", actionLabel = "Estado", onBack = onBack, onActionClick = onOpenStatus) },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Today, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Text(
                text = "Activar alarma",
                style = MaterialTheme.typography.headlineMedium,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Elige cómo quieres que PULSO te avise. Puedes probar el canal antes de guardar.",
                style = MaterialTheme.typography.bodyLarge,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(20.dp))

            if (showNotificationsWarning) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .clip(RoundedCornerShape(16.dp))
                        .background(PulsoSurface)
                        .border(1.dp, PulsoWarningBorder, RoundedCornerShape(16.dp)),
                ) {
                    Column(
                        modifier = Modifier
                            .width(5.dp)
                            .fillMaxHeight()
                            .background(PulsoWarningAccent),
                    ) {}
                    Column(modifier = Modifier.padding(start = 16.dp, top = 20.dp, end = 16.dp, bottom = 16.dp)) {
                        Text(
                            text = "Notificaciones desactivadas",
                            style = MaterialTheme.typography.titleLarge,
                            color = PulsoTextPrimary,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "La configuración no se perdió. Abre Ajustes del teléfono o continúa con vibración y texto.",
                            style = MaterialTheme.typography.bodyLarge,
                            color = PulsoTextSecondary,
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row {
                            TextButton(onClick = { showNotificationsWarning = false }) {
                                Text("CONTINUAR ASÍ", color = PulsoTextSecondary)
                            }
                            TextButton(
                                onClick = {
                                    showNotificationsWarning = false
                                    val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
                                        putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
                                    }
                                    context.startActivity(intent)
                                },
                            ) {
                                Text("ABRIR AJUSTES", color = PulsoTextPrimary)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }

            Text(
                text = "CANALES DE AVISO",
                style = MaterialTheme.typography.labelLarge,
                color = PulsoTextSecondary,
            )
            PulsoSwitchRow(label = "Sonido", checked = soundEnabled, onCheckedChange = { soundEnabled = it })
            PulsoSwitchRow(label = "Vibración", checked = vibrationEnabled, onCheckedChange = { vibrationEnabled = it })
            PulsoSwitchRow(label = "Texto ampliado", checked = largeTextEnabled, onCheckedChange = { largeTextEnabled = it })

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            PulsoPrimaryButton(
                text = "PROBAR CANAL Y ACTIVAR",
                onClick = {
                    onActivated(AlarmChannelSettings(soundEnabled, vibrationEnabled, largeTextEnabled))
                },
            )
            Spacer(modifier = Modifier.height(12.dp))
            TextButton(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text("OMITIR POR AHORA", color = PulsoTextPrimary)
            }
        }
    }
}
