package com.ipcoding.einkaufsliste.feature_item.presentation.items

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ipcoding.einkaufsliste.di.AppModule
import com.ipcoding.einkaufsliste.feature_item.presentation.MainActivity
import com.ipcoding.einkaufsliste.feature_item.presentation.util.Screen
import com.ipcoding.einkaufsliste.ui.theme.EinkaufslisteTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class ItemsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            val navController = rememberNavController()
            EinkaufslisteTheme {
                NavHost(
                    navController = navController,
                    startDestination = Screen.ItemsScreen.route
                ) {
                    composable(route = Screen.ItemsScreen.route) {
                        ItemsScreen(navController = navController)
                    }
                }
            }
        }
    }

    @Test
    fun allButtons_areClickable() {
        composeRule.onNodeWithContentDescription("Sort").assertHasClickAction()
        composeRule.onNodeWithContentDescription("Sort by alpha").assertHasClickAction()
        composeRule.onNodeWithContentDescription("Add").assertHasClickAction()
    }
}