package com.pulso.mobile.ui.screens.today

/** Resultado de intentar guardar un compromiso desde [QuickCaptureSheet]. */
sealed class CaptureResult {
    data class Success(val commitment: Commitment, val scheduledLabel: String) : CaptureResult()
    data class Error(val attemptedDateTime: String) : CaptureResult()
}
