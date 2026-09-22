package com.pulso.mobile.ui.screens.today

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M12 · Confirmar cierre — diálogo mostrado desde "CONFIRMAR CUMPLIMIENTO" en [InProgressScreen]. */
@Composable
fun ConfirmCloseDialog(commitmentTitle: String, onConfirm: () -> Unit, onCancel: () -> Unit) {
    AlertDialog(
        onDismissRequest = onCancel,
        title = {
            Text(
                text = "¿Confirmar cumplimiento?",
                style = MaterialTheme.typography.titleLarge,
                color = PulsoTextPrimary,
            )
        },
        text = {
            Text(
                text = "Se cerrará $commitmentTitle. Podrás deshacerlo durante 10 segundos.",
                style = MaterialTheme.typography.bodyLarge,
                color = PulsoTextSecondary,
            )
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text("CONFIRMAR", color = PulsoTextPrimary)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancel) {
                Text("CANCELAR", color = PulsoTextSecondary)
            }
        },
    )
}
