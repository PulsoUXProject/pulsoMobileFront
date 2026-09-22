package com.pulso.mobile.ui.screens.today

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.BorderStroke
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
import com.pulso.mobile.ui.theme.PulsoSyncAccent
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary
import kotlinx.coroutines.delay

/** M11 · Compromiso en curso — se abre tocando el paso actual en [DetailScreen]. */
@Composable
fun InProgressScreen(
    commitment: Commitment,
    onNavigate: (PulsoDestination) -> Unit,
    onBack: () -> Unit,
    onPause: () -> Unit,
    onConfirmClose: () -> Unit,
    onOpenStatus: () -> Unit,
) {
    var secondsLeft by remember { mutableIntStateOf(12 * 60 + 45) }
    var showConfirmDialog by remember { mutableStateOf(false) }
    val stepsDone = remember { mutableStateListOf(*commitment.steps.map { it.status == StepStatus.DONE }.toTypedArray()) }

    LaunchedEffect(Unit) {
        while (secondsLeft > 0) {
            delay(1000)
            secondsLeft--
        }
    }

    val minutes = secondsLeft / 60
    val seconds = secondsLeft % 60
    val timeLabel = "%d:%02d".format(minutes, seconds)

    Scaffold(
        topBar = { PulsoTopBar(title = "En curso", actionLabel = "Estado", onBack = onBack, onActionClick = onOpenStatus) },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Today, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Text(text = timeLabel, fontSize = 48.sp, color = PulsoTextPrimary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Tiempo restante antes de las ${commitment.fullDateLabel.substringAfter("· ")}",
                style = MaterialTheme.typography.titleMedium,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = commitment.title,
                style = MaterialTheme.typography.headlineMedium,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = PulsoBorder)
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "PASOS", style = MaterialTheme.typography.labelLarge, color = PulsoTextSecondary)
            Spacer(modifier = Modifier.height(12.dp))
            commitment.steps.forEachIndexed { index, step ->
                ChecklistStepRow(
                    label = step.label,
                    done = stepsDone[index],
                    onToggle = { stepsDone[index] = !stepsDone[index] },
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(8.dp))
                    .background(PulsoBackground)
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
                    Icon(Icons.Filled.Schedule, contentDescription = null, tint = PulsoSyncAccent, modifier = Modifier.size(18.dp))
                    Text(
                        text = "Sincronizando · puedes continuar",
                        style = MaterialTheme.typography.titleMedium,
                        color = PulsoTextSecondary,
                        modifier = Modifier.padding(start = 10.dp),
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(16.dp))
            PulsoPrimaryButton(text = "CONFIRMAR CUMPLIMIENTO", onClick = { showConfirmDialog = true })
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onPause,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, PulsoPrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PulsoTextPrimary),
            ) {
                Text("PAUSAR Y VOLVER A HOY")
            }
        }
    }

    if (showConfirmDialog) {
        ConfirmCloseDialog(
            commitmentTitle = commitment.title,
            onConfirm = {
                showConfirmDialog = false
                onConfirmClose()
            },
            onCancel = { showConfirmDialog = false },
        )
    }
}

@Composable
private fun ChecklistStepRow(label: String, done: Boolean, onToggle: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(onClick = onToggle)
            .padding(vertical = 4.dp),
    ) {
        if (done) {
            Box(
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
        } else {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.White)
                    .border(1.dp, PulsoBorderStrong, RoundedCornerShape(4.dp)),
            ) {}
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = PulsoTextPrimary,
            modifier = Modifier.padding(start = 26.dp),
        )
    }
}
