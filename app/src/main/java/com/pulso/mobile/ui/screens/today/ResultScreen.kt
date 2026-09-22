package com.pulso.mobile.ui.screens.today

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.theme.PulsoAlertHeaderTime
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorderStrong
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary
import com.pulso.mobile.ui.theme.PulsoWarningAccent
import kotlinx.coroutines.delay

/** M13 · Resultado y deshacer — se abre al confirmar en [ConfirmCloseDialog]. */
@Composable
fun ResultScreen(
    commitment: Commitment,
    onNavigate: (PulsoDestination) -> Unit,
    onBack: () -> Unit,
    onUndo: () -> Unit,
    onBackToToday: () -> Unit,
    onOpenStatus: () -> Unit,
) {
    var canUndo by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(10_000)
        canUndo = false
    }

    Scaffold(
        topBar = { PulsoTopBar(title = "Resultado", actionLabel = "Estado", onBack = onBack, onActionClick = onOpenStatus) },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Today, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PulsoBorderStrong, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = PulsoWarningAccent)
                Text(
                    text = "Completado · ${commitment.title}",
                    style = MaterialTheme.typography.titleMedium,
                    color = PulsoTextPrimary,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Compromiso completado",
                style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Se cerró a las ${commitment.completionTimeLabel} del ${commitment.dayLabel}, " +
                    "${commitment.completionLeadLabel} antes del vencimiento.",
                style = MaterialTheme.typography.bodyLarge,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(20.dp))
            CommitmentCard(
                commitment = commitment.copy(
                    kicker = "COMPLETADO · ${commitment.completionTimeLabel.uppercase()}",
                    subtitle = "Cerrado con los ${commitment.steps.size} pasos cumplidos",
                    urgency = CommitmentUrgency.NORMAL,
                ),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Puedes deshacer durante 10 segundos. Después el cierre queda en el historial.",
                style = MaterialTheme.typography.bodyMedium,
                color = PulsoTextSecondary,
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(16.dp))
            AnimatedVisibility(visible = canUndo) {
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .background(PulsoTextPrimary)
                            .padding(horizontal = 16.dp, vertical = 14.dp),
                    ) {
                        Text(
                            text = "Completado · ${commitment.title}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.White,
                        )
                        TextButton(onClick = onUndo) {
                            Text("DESHACER", color = PulsoAlertHeaderTime, fontWeight = FontWeight.Bold)
                        }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
            PulsoPrimaryButton(text = "VOLVER A HOY", onClick = onBackToToday)
        }
    }
}
