package com.pulso.mobile.ui.screens.today

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorderStrong
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoSurface
import com.pulso.mobile.ui.theme.PulsoSyncAccent
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M14 · Sin conexión y sincronización — se abre desde el enlace "Estado" del top bar. */
@Composable
fun SyncStatusScreen(
    commitments: List<Commitment>,
    onNavigate: (PulsoDestination) -> Unit,
    onRetry: () -> Unit,
    onBackToToday: () -> Unit,
) {
    Scaffold(
        topBar = { PulsoTopBar(title = "Hoy", actionLabel = "Estado") },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Today, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Column(modifier = Modifier.padding(16.dp)) {
                SyncBanner(
                    icon = Icons.Filled.Info,
                    text = "Sin conexión · guardado localmente",
                    background = PulsoBackground,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Hoy · viernes 22 de agosto",
                    style = MaterialTheme.typography.headlineMedium,
                    color = PulsoTextPrimary,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Puedes seguir creando y editando. Todo queda guardado en el teléfono " +
                        "y se envía cuando vuelva la conexión.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = PulsoTextSecondary,
                )
            }

            LazyColumn(
                modifier = Modifier.weight(1f).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(commitments) { commitment ->
                    val pending = commitment.urgency == CommitmentUrgency.CRITICAL
                    CommitmentCard(
                        commitment = commitment.copy(
                            kicker = if (pending) {
                                "PENDIENTE DE SINCRONIZAR · ${commitment.alarmTimeLabel.uppercase()}"
                            } else {
                                "SINCRONIZADO · ${commitment.alarmTimeLabel.uppercase()}"
                            },
                            subtitle = if (pending) "Se guardó localmente hace 2 minutos" else commitment.subtitle,
                            urgency = CommitmentUrgency.NORMAL,
                        ),
                    )
                }
                item {
                    SyncBanner(
                        icon = Icons.Filled.Schedule,
                        text = "Sincronizando · puedes continuar",
                        background = PulsoSurface,
                    )
                }
            }

            Column(modifier = Modifier.padding(16.dp)) {
                OutlinedButton(
                    onClick = onRetry,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, PulsoPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PulsoTextPrimary),
                ) {
                    Text("REINTENTAR AHORA")
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = onBackToToday, modifier = Modifier.fillMaxWidth()) {
                    Text("VOLVER A HOY", color = PulsoTextPrimary)
                }
            }
        }
    }
}

@Composable
private fun SyncBanner(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String, background: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(8.dp))
            .background(background)
            .border(1.dp, PulsoBorderStrong, RoundedCornerShape(8.dp)),
    ) {
        Column(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight()
                .background(PulsoSyncAccent),
        ) {}
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        ) {
            Icon(icon, contentDescription = null, tint = PulsoSyncAccent, modifier = Modifier.width(18.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                color = PulsoTextSecondary,
                modifier = Modifier.padding(start = 10.dp),
            )
        }
    }
}
