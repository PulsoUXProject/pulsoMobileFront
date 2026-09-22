package com.pulso.mobile.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/**
 * Barra superior común. Por defecto muestra la marca "PULSO" a la izquierda; con [onBack] no nulo
 * muestra un enlace "‹ Atrás" en su lugar (pantallas de detalle/subflujo).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PulsoTopBar(
    title: String,
    actionLabel: String? = null,
    onActionClick: () -> Unit = {},
    onBack: (() -> Unit)? = null,
) {
    CenterAlignedTopAppBar(
        modifier = Modifier.height(64.dp),
        title = {
            Text(
                text = title,
                fontWeight = FontWeight.SemiBold,
                color = PulsoTextPrimary,
            )
        },
        navigationIcon = {
            if (onBack != null) {
                TextButton(onClick = onBack, contentPadding = PaddingValues(horizontal = 12.dp)) {
                    Text(text = "‹ Atrás", color = PulsoTextSecondary)
                }
            } else {
                Text(
                    text = "PULSO",
                    modifier = Modifier,
                    fontWeight = FontWeight.Medium,
                    color = PulsoTextPrimary,
                )
            }
        },
        actions = {
            if (actionLabel != null) {
                TextButton(onClick = onActionClick, contentPadding = PaddingValues(horizontal = 12.dp)) {
                    Text(text = actionLabel, color = PulsoTextSecondary)
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White),
    )
}
