package com.pulso.mobile.ui.screens.alarm

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.screens.today.Commitment
import com.pulso.mobile.ui.screens.today.StepRow
import com.pulso.mobile.ui.screens.today.StepStatus
import com.pulso.mobile.ui.screens.today.UrgencyBadgeRow
import com.pulso.mobile.ui.theme.PulsoAlertHeaderTime
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorder
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoStepDoneAccent
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M08 · Alerta expandida — se abre con "AMPLIAR" desde [ContextualAlertOverlay]. Pantalla completa, sin navegación inferior. */
@Composable
fun AlertExpandedScreen(
    commitment: Commitment,
    onViewDetail: () -> Unit,
    onReschedule: () -> Unit,
    onSnooze: () -> Unit,
) {
    val remainingSteps = commitment.steps.filter { it.status != StepStatus.DONE }

    Scaffold(containerColor = PulsoBackground) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PulsoTextPrimary)
                    .padding(horizontal = 16.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = "ALERTA DE PULSO",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                )
                Text(
                    text = commitment.alarmTimeLabel,
                    style = MaterialTheme.typography.titleMedium,
                    color = PulsoAlertHeaderTime,
                )
            }

            Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                UrgencyBadgeRow(commitment)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = commitment.title,
                    style = MaterialTheme.typography.headlineMedium.copy(fontSize = 32.sp),
                    color = PulsoTextPrimary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = commitment.fullDateLabel,
                    style = MaterialTheme.typography.bodyLarge,
                    color = PulsoTextSecondary,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = commitment.subtitle,
                    style = MaterialTheme.typography.bodyLarge,
                    color = PulsoStepDoneAccent,
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(color = PulsoBorder)
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "QUÉ FALTA",
                    style = MaterialTheme.typography.labelLarge,
                    color = PulsoTextSecondary,
                )
                Spacer(modifier = Modifier.height(12.dp))
                remainingSteps.forEachIndexed { index, step ->
                    val originalIndex = commitment.steps.indexOf(step) + 1
                    StepRow(index = originalIndex, step = step)
                    if (index != remainingSteps.lastIndex) Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(16.dp))
                PulsoPrimaryButton(text = "VER DETALLE", onClick = onViewDetail)
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    onClick = onReschedule,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, PulsoPrimary),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = PulsoTextPrimary),
                ) {
                    Text("REPROGRAMAR")
                }
                Spacer(modifier = Modifier.height(12.dp))
                TextButton(onClick = onSnooze, modifier = Modifier.fillMaxWidth()) {
                    Text("POSPONER 10 MINUTOS", color = PulsoTextPrimary)
                }
            }
        }
    }
}
