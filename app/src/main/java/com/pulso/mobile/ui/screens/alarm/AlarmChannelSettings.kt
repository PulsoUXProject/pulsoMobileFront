package com.pulso.mobile.ui.screens.alarm

/** Canales de aviso elegidos en [AlarmChannelScreen], usados luego en [AlarmStatusScreen]. */
data class AlarmChannelSettings(
    val sound: Boolean,
    val vibration: Boolean,
    val largeText: Boolean,
) {
    fun summaryLabel(): String {
        val parts = buildList {
            if (sound) add("sonido")
            if (vibration) add("vibración")
            if (largeText) add("texto ampliado")
        }
        return if (parts.isEmpty()) "sin canal activo" else parts.joinToString(" + ")
    }
}
