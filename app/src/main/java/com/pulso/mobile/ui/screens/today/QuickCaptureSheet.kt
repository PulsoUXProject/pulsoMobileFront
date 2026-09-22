package com.pulso.mobile.ui.screens.today

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.theme.PulsoBorder
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

/** Fecha y hora usadas como referencia de "ahora" en este mockup (viernes 22 de agosto de 2026, 4:00 p. m.). */
private val NOW = LocalDateTime.of(2026, 8, 22, 16, 0)

private fun formatDateTime(date: LocalDate, time: LocalTime): String {
    val datePart = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    val hour12 = if (time.hour % 12 == 0) 12 else time.hour % 12
    val amPm = if (time.hour < 12) "a. m." else "p. m."
    val timePart = "$hour12:${time.minute.toString().padStart(2, '0')} $amPm"
    return "$datePart · $timePart"
}

/** M02 · Captura rápida — hoja modal que se abre desde el FAB de [TodayScreen]. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuickCaptureSheet(onDismiss: () -> Unit, onResult: (CaptureResult) -> Unit) {
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var name by remember { mutableStateOf("Responder PQRS") }
    var selectedDate by remember { mutableStateOf(NOW.toLocalDate()) }
    var selectedTime by remember { mutableStateOf(NOW.toLocalTime()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val dateTimeLabel = remember(selectedDate, selectedTime) { formatDateTime(selectedDate, selectedTime) }

    ModalBottomSheet(onDismissRequest = onDismiss, sheetState = sheetState) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Text(
                text = "Captura rápida",
                style = MaterialTheme.typography.headlineMedium,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(20.dp))
            QuickCaptureField(
                label = "Nombre del compromiso",
                value = name,
                onValueChange = { name = it },
            )
            Spacer(modifier = Modifier.height(12.dp))
            QuickCaptureDateTimeField(
                label = "Fecha y hora",
                value = dateTimeLabel,
                onClick = { showDatePicker = true },
            )
            Spacer(modifier = Modifier.height(20.dp))
            PulsoPrimaryButton(
                text = "GUARDAR COMPROMISO",
                onClick = {
                    val selectedDateTime = LocalDateTime.of(selectedDate, selectedTime)
                    val result = if (selectedDateTime.isBefore(NOW)) {
                        CaptureResult.Error(attemptedDateTime = dateTimeLabel)
                    } else {
                        CaptureResult.Success(
                            commitment = Commitment(
                                kicker = "NUEVO",
                                title = name,
                                subtitle = "Próximo paso: definir",
                                urgency = CommitmentUrgency.NORMAL,
                            ),
                            scheduledLabel = dateTimeLabel,
                        )
                    }
                    scope.launch {
                        sheetState.hide()
                        onDismiss()
                        onResult(result)
                    }
                },
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
    }

    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(
            initialSelectedDateMillis = selectedDate.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = Instant.ofEpochMilli(millis).atZone(ZoneOffset.UTC).toLocalDate()
                        }
                        showDatePicker = false
                        showTimePicker = true
                    },
                ) {
                    Text("SIGUIENTE")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("CANCELAR")
                }
            },
        ) {
            DatePicker(state = datePickerState)
        }
    }

    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = selectedTime.hour,
            initialMinute = selectedTime.minute,
            is24Hour = false,
        )
        AlertDialog(
            onDismissRequest = { showTimePicker = false },
            title = { Text("Elige la hora") },
            text = { TimePicker(state = timePickerState) },
            confirmButton = {
                TextButton(
                    onClick = {
                        selectedTime = LocalTime.of(timePickerState.hour, timePickerState.minute)
                        showTimePicker = false
                    },
                ) {
                    Text("ACEPTAR")
                }
            },
            dismissButton = {
                TextButton(onClick = { showTimePicker = false }) {
                    Text("CANCELAR")
                }
            },
        )
    }
}

@Composable
private fun QuickCaptureField(label: String, value: String, onValueChange: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PulsoBorder, RoundedCornerShape(8.dp))
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(text = label, style = MaterialTheme.typography.labelMedium, color = PulsoTextSecondary)
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = PulsoTextPrimary),
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 2.dp),
        )
    }
}

/** Campo de fecha y hora de solo lectura: al tocarlo abre el selector de fecha y, luego, el de hora. */
@Composable
private fun QuickCaptureDateTimeField(label: String, value: String, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, PulsoBorder, RoundedCornerShape(8.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Column {
            Text(text = label, style = MaterialTheme.typography.labelMedium, color = PulsoTextSecondary)
            Text(
                text = value,
                style = MaterialTheme.typography.bodyLarge,
                color = PulsoTextPrimary,
                modifier = Modifier.padding(top = 2.dp),
            )
        }
        Icon(Icons.Filled.EditCalendar, contentDescription = "Elegir fecha y hora", tint = PulsoPrimary)
    }
}
