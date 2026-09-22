package com.pulso.mobile.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Mapa de pantallas del Figma "09 · Mockups Mobile" (M01-M16) a rutas de navegación.
 * Solo M01 · Hoy y pendientes está implementada; el resto queda como placeholder
 * para que la navegación de la app no se rompa mientras se van agregando.
 */
sealed class PulsoDestination(val route: String) {
    data object Today : PulsoDestination("hoy") // M01
    data object Confirmation : PulsoDestination("confirmacion") // M03
    data object Detail : PulsoDestination("detalle") // M04
    data object AlarmChannel : PulsoDestination("alarma-canal") // M05
    data object AlarmStatus : PulsoDestination("alarma-estado") // M06
    data object AlertExpanded : PulsoDestination("alerta-expandida") // M08
    data object NewTime : PulsoDestination("nueva-hora") // M09
    data object InProgress : PulsoDestination("en-curso") // M11
    data object Result : PulsoDestination("resultado") // M13
    data object SyncStatus : PulsoDestination("sincronizacion") // M14
    data object TestAlert : PulsoDestination("prueba-alerta") // M16
    data object Calendar : PulsoDestination("calendario") // M07
    data object Settings : PulsoDestination("ajustes") // M15
}

data class BottomNavItem(
    val destination: PulsoDestination,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)

val pulsoBottomNavItems = listOf(
    BottomNavItem(PulsoDestination.Today, "Hoy", Icons.Filled.Home, Icons.Outlined.Home),
    BottomNavItem(PulsoDestination.Calendar, "Calendario", Icons.Filled.CalendarMonth, Icons.Outlined.CalendarMonth),
    BottomNavItem(PulsoDestination.Settings, "Ajustes", Icons.Filled.Settings, Icons.Outlined.Settings),
)
