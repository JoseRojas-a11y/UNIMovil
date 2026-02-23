package org.ccnucleo.unimovil.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.navigator.Navigator
import org.ccnucleo.unimovil.presentation.features.home.ui.HomeScreen
import org.ccnucleo.unimovil.presentation.features.schedule.ui.ScheduleScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedTopAppBar(
    title: String,
    navigator: Navigator
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title) },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Opciones de navegación"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Inicio") },
                    onClick = {
                        expanded = false
                        // replaceAll limpia el stack, ideal para navegación principal
                        navigator.replaceAll(HomeScreen())
                    }
                )
                DropdownMenuItem(
                    text = { Text("Cronograma") },
                    onClick = {
                        expanded = false
                        // push añade la vista al stack
                        navigator.push(ScheduleScreen())
                    }
                )
            }
        }
    )
}