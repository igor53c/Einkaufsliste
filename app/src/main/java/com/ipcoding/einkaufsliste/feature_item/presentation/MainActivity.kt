package com.ipcoding.einkaufsliste.feature_item.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ipcoding.einkaufsliste.feature_item.presentation.add_edit_item.AddEditItemScreen
import com.ipcoding.einkaufsliste.feature_item.presentation.items.ItemsScreen
import com.ipcoding.einkaufsliste.feature_item.presentation.util.Screen
import com.ipcoding.einkaufsliste.ui.theme.EinkaufslisteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EinkaufslisteTheme {
                Surface(
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.ItemsScreen.route
                    ) {
                        composable(route = Screen.ItemsScreen.route) {
                            ItemsScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditItemScreen.route +
                                    "?itemId={itemId}&itemColor={itemColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "itemId"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "itemColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                            )
                        ) {
                            val color = it.arguments?.getInt("itemColor") ?: -1
                            AddEditItemScreen(
                                navController = navController,
                                itemColor = color
                            )
                        }
                    }
                }
            }
        }
    }
}
