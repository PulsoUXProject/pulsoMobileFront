package com.pulso.mobile.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.pulso.mobile.ui.navigation.PulsoDestination
import com.pulso.mobile.ui.navigation.pulsoBottomNavItems
import com.pulso.mobile.ui.theme.PulsoPrimary
import com.pulso.mobile.ui.theme.PulsoPrimaryLight

@Composable
fun PulsoBottomNavBar(
    selected: PulsoDestination,
    onSelect: (PulsoDestination) -> Unit,
) {
    NavigationBar(containerColor = androidx.compose.ui.graphics.Color.White) {
        pulsoBottomNavItems.forEach { item ->
            val isSelected = item.destination == selected
            NavigationBarItem(
                selected = isSelected,
                onClick = { onSelect(item.destination) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
                        contentDescription = item.label,
                    )
                },
                label = { Text(item.label) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = PulsoPrimary,
                    selectedTextColor = PulsoPrimary,
                    indicatorColor = PulsoPrimaryLight,
                ),
            )
        }
    }
}
