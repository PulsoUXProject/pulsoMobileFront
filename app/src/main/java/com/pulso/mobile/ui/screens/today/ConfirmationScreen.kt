package com.pulso.mobile.ui.screens.today

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorder
import com.pulso.mobile.ui.theme.PulsoBorderStrong
import com.pulso.mobile.ui.theme.PulsoOverdueBorder
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M03 · Confirmación y error — resultado de [QuickCaptureSheet]. */
@Composable
fun ConfirmationScreen(
    result: CaptureResult,
    onNavigate: (PulsoDestination) -> Unit,
    onBackToToday: () -> Unit,
    onOpenStatus: () -> Unit,
) {
    Scaffold(
        topBar = { PulsoTopBar(title = "Hoy", actionLabel = "Estado", onActionClick = onOpenStatus) },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Today, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            when (result) {
                is CaptureResult.Success -> SuccessContent(result, onBackToToday)
                is CaptureResult.Error -> ErrorContent(result, onBackToToday)
            }
        }
    }
}

@Composable
private fun SuccessContent(result: CaptureResult.Success, onBackToToday: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PulsoBorderStrong, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Icon(Icons.Filled.Check, contentDescription = null, tint = PulsoPrimary)
        Text(
            text = "Compromiso creado · ${result.scheduledLabel}",
            style = MaterialTheme.typography.titleMedium,
            color = PulsoTextPrimary,
            modifier = Modifier.padding(start = 8.dp),
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    Text(
        text = "Listo, quedó agendado",
        style = MaterialTheme.typography.headlineMedium,
        color = PulsoTextPrimary,
    )
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "${result.commitment.title} se guardó para ${result.scheduledLabel}. Te avisaremos 15 minutos antes.",
        style = MaterialTheme.typography.bodyLarge,
        color = PulsoTextSecondary,
    )
    Spacer(modifier = Modifier.height(20.dp))
    CommitmentCard(commitment = result.commitment)
    Spacer(modifier = Modifier.height(24.dp))
    PulsoPrimaryButton(text = "VOLVER A HOY", onClick = onBackToToday)
}

@Composable
private fun ErrorContent(result: CaptureResult.Error, onBackToToday: () -> Unit) {
    HorizontalDivider(color = PulsoBorder)
    Spacer(modifier = Modifier.height(12.dp))
    Text(
        text = "ESTADO ALTERNO · ERROR DE VALIDACIÓN",
        style = MaterialTheme.typography.labelLarge,
        color = PulsoOverdueBorder,
    )
    Spacer(modifier = Modifier.height(12.dp))
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .border(2.dp, PulsoPrimary, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(text = "Fecha y hora", style = MaterialTheme.typography.labelMedium, color = PulsoTextSecondary)
        Text(
            text = result.attemptedDateTime.ifBlank { "(sin definir)" },
            style = MaterialTheme.typography.bodyLarge,
            color = PulsoTextPrimary,
            modifier = Modifier.padding(top = 2.dp),
        )
    }
    Spacer(modifier = Modifier.height(12.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White)
            .border(2.dp, PulsoPrimary, RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 16.dp),
    ) {
        Text(text = "!", style = MaterialTheme.typography.titleLarge, color = PulsoTextPrimary)
        Text(
            text = "Fecha no válida · elige una hora futura",
            style = MaterialTheme.typography.titleMedium,
            color = PulsoTextPrimary,
            modifier = Modifier.padding(start = 12.dp),
        )
    }
    Spacer(modifier = Modifier.height(16.dp))
    Text(
        text = "Nada se perdió: el nombre que escribiste sigue guardado. Corrige la hora y vuelve a guardar.",
        style = MaterialTheme.typography.bodyMedium,
        color = PulsoTextSecondary,
    )
    Spacer(modifier = Modifier.height(20.dp))
    PulsoPrimaryButton(text = "VOLVER A HOY", onClick = onBackToToday)
}
