package com.pulso.mobile.ui.screens.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.screens.alarm.ContextualAlertOverlay
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoCriticalAccent
import com.pulso.mobile.ui.theme.PulsoOverdueBorder
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M01 · Hoy y pendientes */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayScreen(
    onNavigate: (PulsoDestination) -> Unit,
    onCaptureResult: (CaptureResult) -> Unit,
    onOpenDetail: (Commitment) -> Unit,
    contextualAlert: Commitment?,
    onDismissContextualAlert: () -> Unit,
    onExpandContextualAlert: (Commitment) -> Unit,
    onRescheduleContextualAlert: (Commitment) -> Unit,
    onOpenStatus: () -> Unit,
) {
    var filter by remember { mutableStateOf(TodayFilter.ALL) }
    var showQuickCapture by remember { mutableStateOf(false) }
    val allCommitments = remember { mutableStateListOf(*sampleCommitments.toTypedArray()) }
    val commitments = allCommitments.filteredBy(filter)
    val overdueCount = allCommitments.count { it.urgency == CommitmentUrgency.OVERDUE }

    val isOverdueFilter = filter == TodayFilter.OVERDUE

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            topBar = { PulsoTopBar(title = "Hoy", actionLabel = "Estado", onActionClick = onOpenStatus) },
            bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Today, onSelect = onNavigate) },
            floatingActionButton = {
                if (!isOverdueFilter) {
                    FloatingActionButton(onClick = { showQuickCapture = true }, containerColor = PulsoPrimary) {
                        Icon(Icons.Filled.Add, contentDescription = "Nuevo compromiso", tint = Color.White)
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            containerColor = PulsoBackground,
        ) { padding ->
            Column(modifier = Modifier.fillMaxSize().padding(padding)) {
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Text(
                        text = if (isOverdueFilter) "Vencidos" else "Hoy · viernes 22 de agosto",
                        style = MaterialTheme.typography.headlineMedium,
                        color = PulsoTextPrimary,
                    )
                    Text(
                        text = if (isOverdueFilter) {
                            "${commitments.size} compromisos vencidos · requieren una acción tuya"
                        } else {
                            "${commitments.size} pendientes · $overdueCount vencido · próximo en 15 min"
                        },
                        style = MaterialTheme.typography.titleMedium,
                        color = PulsoTextSecondary,
                        modifier = Modifier.padding(top = 6.dp),
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    TodayFilter.entries.forEach { option ->
                        val accent = when (option) {
                            TodayFilter.ALL -> null
                            TodayFilter.CRITICAL -> PulsoCriticalAccent
                            TodayFilter.OVERDUE -> PulsoOverdueBorder
                        }
                        FilterChip(
                            selected = filter == option,
                            onClick = { filter = option },
                            label = { Text(option.label) },
                            shape = RoundedCornerShape(20.dp),
                            colors = if (accent == null) {
                                FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = PulsoPrimary,
                                    selectedLabelColor = Color.White,
                                )
                            } else {
                                FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Color.White,
                                    selectedLabelColor = PulsoTextPrimary,
                                )
                            },
                            border = accent?.let {
                                FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected = filter == option,
                                    borderColor = it,
                                    selectedBorderColor = it,
                                    selectedBorderWidth = 2.dp,
                                )
                            },
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(commitments) { commitment ->
                        CommitmentCard(commitment = commitment, onClick = { onOpenDetail(commitment) })
                    }
                    if (isOverdueFilter) {
                        item {
                            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                                Text(
                                    text = "Abre un compromiso para reprogramarlo o cerrarlo. El filtro se mantiene hasta que lo cambies.",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = PulsoTextSecondary,
                                )
                                Spacer(modifier = Modifier.height(20.dp))
                                TextButton(onClick = { filter = TodayFilter.ALL }) {
                                    Text("QUITAR FILTRO Y VOLVER A HOY", color = PulsoTextPrimary)
                                }
                            }
                        }
                    }
                }
            }
        }

        if (contextualAlert != null) {
            ContextualAlertOverlay(
                commitment = contextualAlert,
                onSnooze = onDismissContextualAlert,
                onReschedule = { onRescheduleContextualAlert(contextualAlert) },
                onExpand = { onExpandContextualAlert(contextualAlert) },
            )
        }
    }

    if (showQuickCapture) {
        QuickCaptureSheet(
            onDismiss = { showQuickCapture = false },
            onResult = { result ->
                if (result is CaptureResult.Success) {
                    allCommitments.add(0, result.commitment)
                }
                onCaptureResult(result)
            },
        )
    }
}
