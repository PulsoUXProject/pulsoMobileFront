package com.pulso.mobile.ui.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.screens.today.Commitment
import com.pulso.mobile.ui.screens.today.CommitmentCard
import com.pulso.mobile.ui.screens.today.CommitmentUrgency
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorder
import com.pulso.mobile.ui.theme.PulsoCriticalAccent
import com.pulso.mobile.ui.theme.PulsoCriticalText
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoSurface
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

private data class CalendarDay(val weekdayLetter: String, val dayOfMonth: Int, val fullLabel: String)

private val augustDays = listOf(
    CalendarDay("L", 18, "lunes 18 de agosto"),
    CalendarDay("M", 19, "martes 19 de agosto"),
    CalendarDay("M", 20, "miércoles 20 de agosto"),
    CalendarDay("J", 21, "jueves 21 de agosto"),
    CalendarDay("V", 22, "viernes 22 de agosto"),
    CalendarDay("S", 23, "sábado 23 de agosto"),
    CalendarDay("D", 24, "domingo 24 de agosto"),
)

private fun Commitment.dayOfMonth(): Int? = dayLabel.trim().split(" ").getOrNull(1)?.toIntOrNull()

/** M · Calendario — adaptación móvil de la vista semanal del front web (tira de días + agenda del día). */
@Composable
fun CalendarScreen(
    commitments: List<Commitment>,
    onNavigate: (PulsoDestination) -> Unit,
    onOpenStatus: () -> Unit,
    onOpenDetail: (Commitment) -> Unit,
) {
    var selectedDay by remember { mutableIntStateOf(22) }
    val selectedOption = augustDays.first { it.dayOfMonth == selectedDay }
    val dayCommitments = commitments.filter { it.dayOfMonth() == selectedDay }
    val activeCommitments = dayCommitments.filter { it.urgency != CommitmentUrgency.OVERDUE }
    val hasConflict = activeCommitments.size >= 2

    Scaffold(
        topBar = { PulsoTopBar(title = "Calendario", actionLabel = "Estado", onActionClick = onOpenStatus) },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Calendar, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
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
            Text(
                text = selectedOption.fullLabel.replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.headlineMedium,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (dayCommitments.isEmpty()) {
                    "Sin compromisos este día"
                } else {
                    "${dayCommitments.size} compromiso${if (dayCommitments.size == 1) "" else "s"}"
                },
                style = MaterialTheme.typography.titleMedium,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (dayCommitments.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().padding(top = 24.dp), contentAlignment = Alignment.Center) {
                    Text(
                        text = "No hay compromisos este día.",
                        style = MaterialTheme.typography.bodyLarge,
                        color = PulsoTextSecondary,
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(dayCommitments) { commitment ->
                        CommitmentCard(commitment = commitment, onClick = { onOpenDetail(commitment) })
                    }
                    if (hasConflict) {
                        item {
                            val (first, second) = activeCommitments
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
                                        text = "Conflicto: preparación insuficiente entre ${first.title} y ${second.title}",
                                        style = MaterialTheme.typography.titleMedium,
                                        color = PulsoTextPrimary,
                                        modifier = Modifier.padding(start = 12.dp),
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
