package com.pulso.mobile.ui.screens.today

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.theme.PulsoCriticalBadgeBackground
import com.pulso.mobile.ui.theme.PulsoCriticalText
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** Badge "CRÍTICO"/"VENCIDO"/"PROGRAMADO" + texto de vencimiento, usado en M04 y M08. */
@Composable
fun UrgencyBadgeRow(commitment: Commitment) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50))
                .background(PulsoCriticalBadgeBackground)
                .padding(horizontal = 10.dp, vertical = 2.dp),
        ) {
            Text(
                text = commitment.urgency.badgeLabel(),
                style = MaterialTheme.typography.labelLarge,
                color = PulsoCriticalText,
            )
        }
        Text(
            text = commitment.dueInLabel,
            style = MaterialTheme.typography.titleMedium,
            color = PulsoTextSecondary,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
}
