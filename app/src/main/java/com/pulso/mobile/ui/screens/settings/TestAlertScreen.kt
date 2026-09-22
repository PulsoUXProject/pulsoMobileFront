package com.pulso.mobile.ui.screens.settings

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.pulso.mobile.ui.components.PulsoBottomNavBar
import com.pulso.mobile.ui.components.PulsoPrimaryButton
import com.pulso.mobile.ui.components.PulsoTopBar
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.screens.alarm.AlarmChannelSettings
import com.pulso.mobile.ui.screens.today.Commitment
import com.pulso.mobile.ui.screens.today.badgeLabel
import com.pulso.mobile.ui.theme.PulsoBackground
import com.pulso.mobile.ui.theme.PulsoBorderStrong
import com.pulso.mobile.ui.theme.PulsoCriticalAccent
import com.pulso.mobile.ui.theme.PulsoCriticalText
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoStepDoneAccent
import com.pulso.mobile.ui.theme.PulsoStepDoneBackground
import com.pulso.mobile.ui.theme.PulsoSurface
import com.pulso.mobile.ui.theme.PulsoTextPrimary
import com.pulso.mobile.ui.theme.PulsoTextSecondary

/** M16 · Prueba de alerta — se abre desde "PROBAR ALERTA" en [AccessibilityScreen]. */
@Composable
fun TestAlertScreen(
    commitment: Commitment,
    settings: AlarmChannelSettings,
    onNavigate: (PulsoDestination) -> Unit,
    onRepeat: () -> Unit,
    onViewRealAlert: () -> Unit,
) {
    Scaffold(
        topBar = { PulsoTopBar(title = "Ajustes", actionLabel = "Ayuda") },
        bottomBar = { PulsoBottomNavBar(selected = PulsoDestination.Settings, onSelect = onNavigate) },
        containerColor = PulsoBackground,
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
        ) {
            Text(
                text = "Prueba de alerta",
                style = MaterialTheme.typography.headlineMedium,
                color = PulsoTextPrimary,
            )
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, PulsoBorderStrong, RoundedCornerShape(8.dp))
                    .padding(horizontal = 16.dp, vertical = 16.dp),
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = PulsoPrimary)
                Text(
                    text = "${settings.summaryLabel().replaceFirstChar { it.uppercase() }} listos",
                    style = MaterialTheme.typography.titleMedium,
                    color = PulsoTextPrimary,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
            Spacer(modifier = Modifier.height(20.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PulsoSurface)
                    .dashedBorder(PulsoCriticalText, 2.dp, 12.dp),
            ) {
                Column(
                    modifier = Modifier
                        .width(5.dp)
                        .fillMaxHeight()
                        .background(PulsoCriticalAccent),
                ) {}
                Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)) {
                    Text(
                        text = "PULSO · PRUEBA",
                        style = MaterialTheme.typography.labelLarge,
                        color = PulsoTextSecondary,
                    )
                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = commitment.title,
                        style = MaterialTheme.typography.titleLarge,
                        color = PulsoTextPrimary,
                    )
                    Spacer(modifier = Modifier.height(9.dp))
                    Text(
                        text = "${commitment.dueInLabel} · ${commitment.urgency.badgeLabel().lowercase().replaceFirstChar { it.uppercase() }}",
                        style = MaterialTheme.typography.titleMedium,
                        color = PulsoTextSecondary,
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Así se verá y sonará la alerta con tus ajustes actuales. Si no la escuchaste, revisa el volumen del teléfono.",
                style = MaterialTheme.typography.bodyLarge,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "RESULTADO DE LA PRUEBA",
                style = MaterialTheme.typography.labelLarge,
                color = PulsoTextSecondary,
            )
            Spacer(modifier = Modifier.height(12.dp))
            TestResultRow(label = "Sonido reproducido", passed = settings.sound)
            Spacer(modifier = Modifier.height(16.dp))
            TestResultRow(label = "Vibración activada", passed = settings.vibration)
            Spacer(modifier = Modifier.height(16.dp))
            TestResultRow(label = "Texto ampliado aplicado", passed = settings.largeText)

            Spacer(modifier = Modifier.weight(1f))
            Spacer(modifier = Modifier.height(20.dp))
            OutlinedButton(
                onClick = onRepeat,
                modifier = Modifier.fillMaxWidth().height(48.dp),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, PulsoPrimary),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = PulsoTextPrimary),
            ) {
                Text("REPETIR PRUEBA")
            }
            Spacer(modifier = Modifier.height(12.dp))
            PulsoPrimaryButton(text = "VER ALERTA REAL", onClick = onViewRealAlert)
        }
    }
}

@Composable
private fun TestResultRow(label: String, passed: Boolean) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(if (passed) PulsoStepDoneBackground else Color.White)
                .border(1.dp, if (passed) PulsoStepDoneAccent else PulsoBorderStrong, CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            if (passed) {
                Icon(
                    Icons.Filled.Check,
                    contentDescription = "Superado",
                    tint = PulsoStepDoneAccent,
                    modifier = Modifier.size(14.dp),
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = PulsoTextPrimary,
            modifier = Modifier.padding(start = 26.dp),
        )
    }
}

private fun Modifier.dashedBorder(color: Color, width: Dp, cornerRadius: Dp): Modifier = drawWithContent {
    drawContent()
    val stroke = Stroke(
        width = width.toPx(),
        pathEffect = PathEffect.dashPathEffect(floatArrayOf(14f, 10f), 0f),
    )
    drawRoundRect(
        color = color,
        style = stroke,
        cornerRadius = CornerRadius(cornerRadius.toPx()),
        size = Size(size.width, size.height),
    )
}
