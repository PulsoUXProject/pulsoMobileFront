package com.pulso.mobile.ui.screens.alarm

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.screens.today.Commitment
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorder
import com.pulso.mobile.ui.theme.PulsoBorderStrong
import com.pulso.mobile.ui.theme.PulsoCriticalAccent
import com.pulso.mobile.ui.theme.PulsoCriticalText
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoSurface
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

private data class DayOption(val weekdayLetter: String, val dayOfMonth: Int)

private val augustDays = listOf(
    DayOption("L", 18),
    DayOption("M", 19),
    DayOption("M", 20),
    DayOption("J", 21),
    DayOption("V", 22),
    DayOption("S", 23),
    DayOption("D", 24),
)

private val suggestedTimes = listOf("4:30 p. m.", "5:00 p. m.", "Mañana 9:00 a. m.")

/** M09 · Nueva fecha y hora — "REPROGRAMAR" desde M04/M07/M08. */
@Composable
fun NewTimeScreen(
    commitment: Commitment,
    onBack: () -> Unit,
    onConfirm: (String) -> Unit,
    onCancel: () -> Unit,
    onOpenStatus: () -> Unit,
) {
    var selectedDay by remember { mutableIntStateOf(22) }
    var selectedTimeIndex by remember { mutableStateOf(0) }
    val selectedTime = suggestedTimes[selectedTimeIndex]
    val newDateTimeLabel = "$selectedDay/08/2026 · $selectedTime"

    Scaffold(
        topBar = { PulsoTopBar(title = "Nueva hora", actionLabel = "Estado", onBack = onBack, onActionClick = onOpenStatus) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Text(
                text = "Reprogramar ${commitment.title}",
                style = MaterialTheme.typography.headlineMedium,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PulsoBorderStrong, RoundedCornerShape(8.dp))
                    .padding(horizontal = 12.dp, vertical = 8.dp),
            ) {
                Text(text = "Fecha y hora", style = MaterialTheme.typography.labelMedium, color = PulsoTextSecondary)
                Text(
                    text = newDateTimeLabel,
                    style = MaterialTheme.typography.titleMedium,
                    color = PulsoTextPrimary,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "AGOSTO 2026", style = MaterialTheme.typography.labelLarge, color = PulsoTextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                augustDays.forEach { day ->
                    val selected = day.dayOfMonth == selectedDay
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(8.dp))
                            .background(if (selected) PulsoPrimary else PulsoSurface)
                            .border(1.dp, if (selected) PulsoPrimary else PulsoBorder, RoundedCornerShape(8.dp))
                            .clickable { selectedDay = day.dayOfMonth }
                            .padding(vertical = 8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = day.weekdayLetter,
                            style = MaterialTheme.typography.labelMedium,
                            color = if (selected) Color.White else PulsoTextSecondary,
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "${day.dayOfMonth}",
                            style = MaterialTheme.typography.titleLarge,
                            color = if (selected) Color.White else PulsoTextPrimary,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "HORAS SUGERIDAS", style = MaterialTheme.typography.labelLarge, color = PulsoTextSecondary)
            Spacer(modifier = Modifier.height(8.dp))
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                suggestedTimes.forEachIndexed { index, time ->
                    FilterChip(
                        selected = index == selectedTimeIndex,
                        onClick = { selectedTimeIndex = index },
                        label = { Text(time) },
                        shape = RoundedCornerShape(20.dp),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = PulsoPrimary,
                            selectedLabelColor = Color.White,
                        ),
                    )
                }
            }

            if (selectedTimeIndex == 0) {
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min)
                        .clip(RoundedCornerShape(8.dp))
                        .background(PulsoSurface)
                        .border(2.dp, PulsoCriticalText, RoundedCornerShape(8.dp)),
                ) {
                    Column(
                        modifier = Modifier
                            .width(5.dp)
                            .fillMaxHeight()
                            .background(PulsoCriticalAccent),
                    ) {}
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                    ) {
                        Text(text = "!", style = MaterialTheme.typography.titleLarge, color = PulsoTextPrimary)
                        Text(
                            text = "Se cruza con Reunión de equipo",
                            style = MaterialTheme.typography.titleMedium,
                            color = PulsoTextPrimary,
                            modifier = Modifier.padding(start = 12.dp),
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Puedes confirmar igual o elegir otra hora. Nada se pierde mientras decides.",
                style = MaterialTheme.typography.bodyMedium,
                color = PulsoTextSecondary,
            )

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            PulsoPrimaryButton(text = "CONFIRMAR NUEVA HORA", onClick = { onConfirm(newDateTimeLabel) })
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = onCancel,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, PulsoPrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PulsoTextPrimary),
            ) {
                Text("CANCELAR")
            }
        }
    }
}
