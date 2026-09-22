package com.pulso.mobile.ui.screens.today

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorder
import com.pulso.mobile.ui.theme.PulsoCriticalAccent
import com.pulso.mobile.ui.theme.PulsoCriticalText
import com.pulso.mobile.ui.theme.PulsoNormalAccent
import com.pulso.mobile.ui.theme.PulsoOverdueAccent
import com.pulso.mobile.ui.theme.PulsoOverdueBorder
import com.pulso.mobile.ui.theme.PulsoSurface
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

private data class UrgencyStyle(
    val accent: Color,
    val kickerColor: Color,
    val cardBackground: Color,
    val borderColor: Color,
    val borderWidth: Int,
)

private fun CommitmentUrgency.style(): UrgencyStyle = when (this) {
    CommitmentUrgency.CRITICAL -> UrgencyStyle(
        accent = PulsoCriticalAccent,
        kickerColor = PulsoCriticalText,
        cardBackground = PulsoBackground,
        borderColor = PulsoBorder,
        borderWidth = 1,
    )
    CommitmentUrgency.NORMAL -> UrgencyStyle(
        accent = PulsoNormalAccent,
        kickerColor = PulsoTextSecondary,
        cardBackground = PulsoSurface,
        borderColor = PulsoBorder,
        borderWidth = 1,
    )
    CommitmentUrgency.OVERDUE -> UrgencyStyle(
        accent = PulsoOverdueAccent,
        kickerColor = PulsoOverdueBorder,
        cardBackground = PulsoSurface,
        borderColor = PulsoOverdueBorder,
        borderWidth = 2,
    )
}

@Composable
fun CommitmentCard(commitment: Commitment, modifier: Modifier = Modifier, onClick: (() -> Unit)? = null) {
    val style = commitment.urgency.style()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .clip(RoundedCornerShape(12.dp))
            .background(style.cardBackground)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .border(style.borderWidth.dp, style.borderColor, RoundedCornerShape(12.dp)),
    ) {
        Column(
            modifier = Modifier
                .width(5.dp)
                .fillMaxHeight()
                .background(style.accent),
        ) {}
        Column(modifier = Modifier.padding(start = 14.dp, top = 12.dp, end = 16.dp, bottom = 12.dp)) {
            Text(
                text = commitment.kicker,
                style = MaterialTheme.typography.labelLarge,
                color = style.kickerColor,
            )
            Text(
                text = commitment.title,
                style = MaterialTheme.typography.titleLarge,
                color = PulsoTextPrimary,
                modifier = Modifier.padding(top = 6.dp),
            )
            Text(
                text = commitment.subtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = PulsoTextSecondary,
                modifier = Modifier.padding(top = 4.dp),
            )
        }
    }
}
