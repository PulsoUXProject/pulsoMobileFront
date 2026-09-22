package com.pulso.mobile.ui.screens.alarm

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.components.PulsoSwitchRow
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.screens.today.Commitment
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorder
import com.pulso.mobile.ui.theme.PulsoBorderStrong
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary
import com.pulso.mobile.ui.theme.PulsoWarningAccent

/** M06 · Estado de alarma — resultado de [AlarmChannelScreen]. */
@Composable
fun AlarmStatusScreen(
    commitment: Commitment,
    channelSettings: AlarmChannelSettings,
    onNavigate: (PulsoDestination) -> Unit,
    onBack: () -> Unit,
    onBackToToday: () -> Unit,
    onTestAgain: () -> Unit,
    onOpenStatus: () -> Unit,
) {
    var repeatEnabled by remember { mutableStateOf(true) }

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
            Row(
                verticalAlignment = Alignment.CenterVertically,
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
                        .background(PulsoWarningAccent),
                ) {}
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                ) {
                    Icon(Icons.Filled.Check, contentDescription = null, tint = PulsoPrimary)
                    Text(
                        text = "${channelSettings.summaryLabel().replaceFirstChar { it.uppercase() }} listos",
                        style = MaterialTheme.typography.titleMedium,
                        color = PulsoTextPrimary,
                        modifier = Modifier.padding(start = 8.dp),
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Alarma activa",
                style = MaterialTheme.typography.headlineMedium,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = commitment.alarmTimeLabel,
                fontSize = 48.sp,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Sonará el ${commitment.dayLabel}, 15 minutos antes de ${commitment.title}.",
                style = MaterialTheme.typography.bodyLarge,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = PulsoBorder)
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Canal: ${channelSettings.summaryLabel()}",
                style = MaterialTheme.typography.titleMedium,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (repeatEnabled) {
                    "Se repetirá cada 5 minutos hasta que confirmes."
                } else {
                    "Sonará una sola vez."
                },
                style = MaterialTheme.typography.titleMedium,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            PulsoSwitchRow(label = "Repetir alerta", checked = repeatEnabled, onCheckedChange = { repeatEnabled = it })

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                onClick = onTestAgain,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, PulsoPrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PulsoTextPrimary),
            ) {
                Text("PROBAR DE NUEVO")
            }
            Spacer(modifier = Modifier.height(12.dp))
            PulsoPrimaryButton(text = "VOLVER A HOY", onClick = onBackToToday)
        }
    }
}
