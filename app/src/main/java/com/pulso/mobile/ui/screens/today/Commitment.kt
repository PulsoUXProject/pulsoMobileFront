package com.pulso.mobile.ui.screens.today

enum class CommitmentUrgency { CRITICAL, NORMAL, OVERDUE }

enum class StepStatus { DONE, CURRENT, UPCOMING }

data class CommitmentStep(val label: String, val status: StepStatus)

data class Commitment(
    val kicker: String,
    val title: String,
    val subtitle: String,
    val urgency: CommitmentUrgency,
    val fullDateLabel: String = "",
    val dueInLabel: String = "",
    val dayLabel: String = "",
    val alarmTimeLabel: String = "",
    val completionTimeLabel: String = "",
    val completionLeadLabel: String = "",
    val steps: List<CommitmentStep> = emptyList(),
)

fun CommitmentUrgency.badgeLabel(): String = when (this) {
    CommitmentUrgency.CRITICAL -> "CRÍTICO"
    CommitmentUrgency.NORMAL -> "PROGRAMADO"
    CommitmentUrgency.OVERDUE -> "VENCIDO"
}

enum class TodayFilter(val label: String) {
    ALL("Todos"),
    CRITICAL("Críticos"),
    OVERDUE("Vencidos"),
}

val sampleCommitments = listOf(
    Commitment(
        kicker = "VIERNES · 4:00 P. M. · CRÍTICO",
        title = "Responder PQRS",
        subtitle = "Próximo paso: revisar el caso",
        urgency = CommitmentUrgency.CRITICAL,
        fullDateLabel = "Viernes 22 de agosto · 4:00 p. m.",
        dueInLabel = "Vence en 15 minutos",
        dayLabel = "viernes 22 de agosto",
        alarmTimeLabel = "3:45 p. m.",
        completionTimeLabel = "3:52 p. m.",
        completionLeadLabel = "8 minutos",
        steps = listOf(
            CommitmentStep("Recibir el caso", StepStatus.DONE),
            CommitmentStep("Redactar respuesta", StepStatus.CURRENT),
            CommitmentStep("Validar y enviar", StepStatus.UPCOMING),
        ),
    ),
    Commitment(
        kicker = "HOY · 2:00 P. M.",
        title = "Reunión de equipo",
        subtitle = "Próximo paso: preparar la agenda",
        urgency = CommitmentUrgency.NORMAL,
        fullDateLabel = "Viernes 22 de agosto · 2:00 p. m.",
        dueInLabel = "Vence en 2 horas",
        dayLabel = "viernes 22 de agosto",
        alarmTimeLabel = "1:45 p. m.",
        completionTimeLabel = "1:55 p. m.",
        completionLeadLabel = "5 minutos",
        steps = listOf(
            CommitmentStep("Convocar al equipo", StepStatus.DONE),
            CommitmentStep("Preparar la agenda", StepStatus.CURRENT),
            CommitmentStep("Enviar minuta", StepStatus.UPCOMING),
        ),
    ),
    Commitment(
        kicker = "VENCIDO · REQUIERE ACCIÓN",
        title = "Enviar informe mensual",
        subtitle = "Próximo paso: reprogramar o cerrar",
        urgency = CommitmentUrgency.OVERDUE,
        fullDateLabel = "Jueves 21 de agosto · 6:00 p. m.",
        dueInLabel = "Venció hace 18 horas",
        dayLabel = "jueves 21 de agosto",
        alarmTimeLabel = "5:45 p. m.",
        completionTimeLabel = "5:58 p. m.",
        completionLeadLabel = "2 minutos",
        steps = listOf(
            CommitmentStep("Consolidar cifras", StepStatus.DONE),
            CommitmentStep("Redactar informe", StepStatus.DONE),
            CommitmentStep("Enviar o reprogramar", StepStatus.CURRENT),
        ),
    ),
)

fun List<Commitment>.filteredBy(filter: TodayFilter): List<Commitment> = when (filter) {
    TodayFilter.ALL -> this
    TodayFilter.CRITICAL -> filter { it.urgency == CommitmentUrgency.CRITICAL }
    TodayFilter.OVERDUE -> filter { it.urgency == CommitmentUrgency.OVERDUE }
}
