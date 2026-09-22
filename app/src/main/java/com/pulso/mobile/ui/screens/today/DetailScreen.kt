package com.pulso.mobile.ui.screens.today

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorder
import com.pulso.mobile.ui.theme.PulsoBorderStrong
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoStepDoneAccent
import com.pulso.mobile.ui.theme.PulsoStepDoneBackground
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M04 · Detalle del compromiso */
@Composable
fun DetailScreen(
    commitment: Commitment,
    alarmActive: Boolean,
    onNavigate: (PulsoDestination) -> Unit,
    onBack: () -> Unit,
    onActivateAlarm: () -> Unit,
    onReschedule: () -> Unit,
    onStartCurrentStep: () -> Unit,
    onOpenStatus: () -> Unit,
) {
    Scaffold(
        topBar = { PulsoTopBar(title = "Detalle", actionLabel = "Estado", onBack = onBack, onActionClick = onOpenStatus) },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Today, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            UrgencyBadgeRow(commitment)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = commitment.title,
                style = MaterialTheme.typography.headlineMedium,
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
                text = "PASOS DEL COMPROMISO",
                style = MaterialTheme.typography.labelLarge,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            commitment.steps.forEachIndexed { index, step ->
                StepRow(
                    index = index + 1,
                    step = step,
                    onClick = if (step.status == StepStatus.CURRENT) onStartCurrentStep else null,
                )
                if (index != commitment.steps.lastIndex) Spacer(modifier = Modifier.height(12.dp))
            }
            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(16.dp))
            PulsoPrimaryButton(
                text = if (alarmActive) "ALARMA ACTIVADA" else "ACTIVAR ALARMA",
                onClick = { if (!alarmActive) onActivateAlarm() },
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onReschedule,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, PulsoPrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PulsoTextPrimary),
            ) {
                Text("REPROGRAMAR")
            }
        }
    }
}

@Composable
fun StepRow(index: Int, step: CommitmentStep, onClick: (() -> Unit)? = null) {
    val rowBackground = if (step.status == StepStatus.CURRENT) PulsoBackground else Color.White
    val labelColor = if (step.status == StepStatus.UPCOMING) PulsoTextSecondary else PulsoTextPrimary

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(rowBackground)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
            .border(1.dp, PulsoBorderStrong, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        when (step.status) {
            StepStatus.DONE -> Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(PulsoStepDoneBackground)
                    .border(1.dp, PulsoStepDoneAccent, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Completado",
                    tint = PulsoStepDoneAccent,
                    modifier = Modifier.size(14.dp),
                )
            }
            else -> Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.White)
                    .border(1.dp, PulsoPrimary, CircleShape),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "$index", fontSize = 14.sp, color = PulsoTextPrimary)
            }
        }
        Text(
            text = step.label,
            style = MaterialTheme.typography.bodyLarge,
            color = labelColor,
            modifier = Modifier.padding(start = 18.dp),
        )
    }
}
