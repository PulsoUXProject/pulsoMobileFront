package com.pulso.mobile.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pulso.mobile.ui.screens.alarm.AlarmChannelScreen
import com.pulso.mobile.ui.screens.alarm.AlarmChannelSettings
import com.pulso.mobile.ui.screens.alarm.AlarmStatusScreen
import com.pulso.mobile.ui.screens.alarm.AlertExpandedScreen
import com.pulso.mobile.ui.screens.alarm.NewTimeScreen
import com.pulso.mobile.ui.screens.calendar.CalendarScreen
import com.pulso.mobile.ui.screens.settings.AccessibilityScreen
import com.pulso.mobile.ui.screens.settings.TestAlertScreen
import com.pulso.mobile.ui.screens.today.CaptureResult
import com.pulso.mobile.ui.screens.today.Commitment
import com.pulso.mobile.ui.screens.today.ConfirmationScreen
import com.pulso.mobile.ui.screens.today.DetailScreen
import com.pulso.mobile.ui.screens.today.InProgressScreen
import com.pulso.mobile.ui.screens.today.ResultScreen
import com.pulso.mobile.ui.screens.today.SyncStatusScreen
import com.pulso.mobile.ui.screens.today.TodayScreen
import com.pulso.mobile.ui.screens.today.sampleCommitments

@Composable
fun PulsoNavHost(navController: NavHostController = rememberNavController()) {
    var lastCaptureResult by remember { mutableStateOf<CaptureResult?>(null) }
    var selectedCommitment by remember { mutableStateOf<Commitment?>(null) }
    var alarmActive by remember { mutableStateOf(false) }
    var alarmChannelSettings by remember { mutableStateOf<AlarmChannelSettings?>(null) }
    var contextualAlertCommitment by remember { mutableStateOf<Commitment?>(null) }
    var testAlertSettings by remember { mutableStateOf<AlarmChannelSettings?>(null) }

    val navigate: (PulsoDestination) -> Unit = { destination ->
        navController.navigate(destination.route) {
            popUpTo(PulsoDestination.Today.route) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    val openStatus: () -> Unit = { navController.navigate(PulsoDestination.SyncStatus.route) }

    NavHost(navController = navController, startDestination = PulsoDestination.Today.route) {
        composable(PulsoDestination.Today.route) {
            TodayScreen(
                onNavigate = navigate,
                onCaptureResult = { result ->
                    lastCaptureResult = result
                    navController.navigate(PulsoDestination.Confirmation.route)
                },
                onOpenDetail = { commitment ->
                    selectedCommitment = commitment
                    alarmActive = false
                    navController.navigate(PulsoDestination.Detail.route)
                },
                contextualAlert = contextualAlertCommitment,
                onDismissContextualAlert = { contextualAlertCommitment = null },
                onExpandContextualAlert = {
                    contextualAlertCommitment = null
                    navController.navigate(PulsoDestination.AlertExpanded.route)
                },
                onRescheduleContextualAlert = { commitment ->
                    selectedCommitment = commitment
                    contextualAlertCommitment = null
                    navController.navigate(PulsoDestination.NewTime.route)
                },
                onOpenStatus = openStatus,
            )
        }
        composable(PulsoDestination.Confirmation.route) {
            val result = lastCaptureResult
            if (result != null) {
                ConfirmationScreen(
                    result = result,
                    onNavigate = navigate,
                    onBackToToday = { navController.popBackStack(PulsoDestination.Today.route, inclusive = false) },
                    onOpenStatus = openStatus,
                )
            }
        }
        composable(PulsoDestination.Detail.route) {
            val commitment = selectedCommitment
            if (commitment != null) {
                DetailScreen(
                    commitment = commitment,
                    alarmActive = alarmActive,
                    onNavigate = navigate,
                    onBack = { navController.popBackStack() },
                    onActivateAlarm = { navController.navigate(PulsoDestination.AlarmChannel.route) },
                    onReschedule = { navController.navigate(PulsoDestination.NewTime.route) },
                    onStartCurrentStep = { navController.navigate(PulsoDestination.InProgress.route) },
                    onOpenStatus = openStatus,
                )
            }
        }
        composable(PulsoDestination.InProgress.route) {
            val commitment = selectedCommitment
            if (commitment != null) {
                InProgressScreen(
                    commitment = commitment,
                    onNavigate = navigate,
                    onBack = { navController.popBackStack() },
                    onPause = { navController.popBackStack(PulsoDestination.Today.route, inclusive = false) },
                    onConfirmClose = { navController.navigate(PulsoDestination.Result.route) },
                    onOpenStatus = openStatus,
                )
            }
        }
        composable(PulsoDestination.Result.route) {
            val commitment = selectedCommitment
            if (commitment != null) {
                ResultScreen(
                    commitment = commitment,
                    onNavigate = navigate,
                    onBack = { navController.popBackStack() },
                    onUndo = { navController.popBackStack(PulsoDestination.Today.route, inclusive = false) },
                    onBackToToday = { navController.popBackStack(PulsoDestination.Today.route, inclusive = false) },
                    onOpenStatus = openStatus,
                )
            }
        }
        composable(PulsoDestination.AlarmChannel.route) {
            AlarmChannelScreen(
                onNavigate = navigate,
                onBack = { navController.popBackStack() },
                onActivated = { settings ->
                    alarmActive = true
                    alarmChannelSettings = settings
                    navController.navigate(PulsoDestination.AlarmStatus.route)
                },
                onOpenStatus = openStatus,
            )
        }
        composable(PulsoDestination.AlarmStatus.route) {
            val commitment = selectedCommitment
            val settings = alarmChannelSettings
            if (commitment != null && settings != null) {
                AlarmStatusScreen(
                    commitment = commitment,
                    channelSettings = settings,
                    onNavigate = navigate,
                    onBack = { navController.popBackStack() },
                    onBackToToday = { navController.popBackStack(PulsoDestination.Today.route, inclusive = false) },
                    onTestAgain = {
                        contextualAlertCommitment = commitment
                        navController.popBackStack(PulsoDestination.Today.route, inclusive = false)
                    },
                    onOpenStatus = openStatus,
                )
            }
        }
        composable(PulsoDestination.AlertExpanded.route) {
            val commitment = selectedCommitment
            if (commitment != null) {
                AlertExpandedScreen(
                    commitment = commitment,
                    onViewDetail = {
                        navController.navigate(PulsoDestination.Detail.route) {
                            popUpTo(PulsoDestination.Today.route) { inclusive = false }
                        }
                    },
                    onReschedule = { navController.navigate(PulsoDestination.NewTime.route) },
                    onSnooze = { navController.popBackStack() },
                )
            }
        }
        composable(PulsoDestination.NewTime.route) {
            val commitment = selectedCommitment
            if (commitment != null) {
                NewTimeScreen(
                    commitment = commitment,
                    onBack = { navController.popBackStack() },
                    onConfirm = { navController.popBackStack() },
                    onCancel = { navController.popBackStack() },
                    onOpenStatus = openStatus,
                )
            }
        }
        composable(PulsoDestination.SyncStatus.route) {
            SyncStatusScreen(
                commitments = sampleCommitments,
                onNavigate = navigate,
                onRetry = { navController.popBackStack() },
                onBackToToday = { navController.popBackStack(PulsoDestination.Today.route, inclusive = false) },
            )
        }
        composable(PulsoDestination.Calendar.route) {
            CalendarScreen(
                commitments = sampleCommitments,
                onNavigate = navigate,
                onOpenStatus = openStatus,
                onOpenDetail = { commitment ->
                    selectedCommitment = commitment
                    alarmActive = false
                    navController.navigate(PulsoDestination.Detail.route)
                },
            )
        }
        composable(PulsoDestination.Settings.route) {
            AccessibilityScreen(
                onNavigate = navigate,
                onTestAlert = { settings ->
                    testAlertSettings = settings
                    navController.navigate(PulsoDestination.TestAlert.route)
                },
            )
        }
        composable(PulsoDestination.TestAlert.route) {
            val settings = testAlertSettings
            if (settings != null) {
                TestAlertScreen(
                    commitment = sampleCommitments[0],
                    settings = settings,
                    onNavigate = navigate,
                    onRepeat = { navController.popBackStack() },
                    onViewRealAlert = {
                        contextualAlertCommitment = sampleCommitments[0]
                        navController.navigate(PulsoDestination.Today.route) {
                            popUpTo(PulsoDestination.Today.route) { inclusive = false }
                        }
                    },
                )
            }
        }
    }
}
