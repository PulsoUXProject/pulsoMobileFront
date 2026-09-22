package com.pulso.mobile.ui.screens.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.screens.today.Commitment
import com.pulso.mobile.ui.screens.today.badgeLabel
import com.pulso.mobile.ui.theme.PulsoCriticalAccent
import com.pulso.mobile.ui.theme.PulsoCriticalText
import com.pulso.mobile.ui.theme.PulsoSurface
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M07 · Alerta contextual — se dispara desde "PROBAR DE NUEVO" en [AlarmStatusScreen], sobre [com.pulso.mobile.ui.screens.today.TodayScreen]. */
@Composable
fun ContextualAlertOverlay(
    commitment: Commitment,
    onSnooze: () -> Unit,
    onReschedule: () -> Unit,
    onExpand: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(PulsoTextPrimary.copy(alpha = 0.3f))
            .padding(16.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .clip(RoundedCornerShape(12.dp))
                .background(PulsoSurface)
                .border(2.dp, PulsoCriticalText, RoundedCornerShape(12.dp)),
        ) {
            Column(
                modifier = Modifier
                    .width(5.dp)
                    .fillMaxHeight()
                    .background(PulsoCriticalAccent),
            ) {}
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                Text(
                    text = "PULSO · AHORA ${commitment.alarmTimeLabel.uppercase()}",
                    style = MaterialTheme.typography.labelLarge,
                    color = PulsoTextSecondary,
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = commitment.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = PulsoTextPrimary,
                )
                Spacer(modifier = Modifier.height(9.dp))
                Text(
                    text = "${commitment.dueInLabel} · ${commitment.urgency.badgeLabel().lowercase().replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.titleMedium,
                    color = PulsoTextSecondary,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = commitment.subtitle,
                    style = MaterialTheme.typography.titleMedium,
                    color = PulsoTextSecondary,
                )
                Spacer(modifier = Modifier.height(12.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    TextButton(onClick = onSnooze) {
                        Text("POSPONER", color = PulsoTextPrimary)
                    }
                    TextButton(onClick = onReschedule) {
                        Text("REPROGRAMAR", color = PulsoTextPrimary)
                    }
                    TextButton(onClick = onExpand) {
                        Text("AMPLIAR", color = PulsoTextPrimary)
                    }
                }
            }
        }
    }
}
