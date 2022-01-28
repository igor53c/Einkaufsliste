package com.ipcoding.einkaufsliste.feature_item.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ipcoding.einkaufsliste.core.util.TestTags
import com.ipcoding.einkaufsliste.di.AppModule
import com.ipcoding.einkaufsliste.feature_item.presentation.add_edit_item.AddEditItemScreen
import com.ipcoding.einkaufsliste.feature_item.presentation.items.ItemsScreen
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
class ItemsEndToEndTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @ExperimentalAnimationApi
    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            EinkaufslisteTheme {
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

    @Test
    fun saveNewItem() {
        // Click on button to get to add item screen
        composeRule.onNodeWithContentDescription("Add").performClick()

        // Enter texts in title text fields
        composeRule
            .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
            .performTextInput("test-title")
        // Save the new
        composeRule.onNodeWithContentDescription("Save").performClick()

        // Make sure there is a item in the list with our title
        composeRule.onNodeWithText("test-title").assertIsDisplayed()
    }

    @Test
    fun saveNewItems_orderByTitleDescending() {
        for(i in 1..3) {
            // Click on button to get to add item screen
            composeRule.onNodeWithContentDescription("Add").performClick()

            // Enter texts in title and content text fields
            composeRule
                .onNodeWithTag(TestTags.TITLE_TEXT_FIELD)
                .performTextInput(i.toString())
            // Save the new
            composeRule.onNodeWithContentDescription("Save").performClick()
        }

        composeRule.onNodeWithText("1").assertIsDisplayed()
        composeRule.onNodeWithText("2").assertIsDisplayed()
        composeRule.onNodeWithText("3").assertIsDisplayed()

        composeRule
            .onNodeWithContentDescription("Sort by alpha")
            .performClick()
            .performClick()

        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[0]
            .assertTextContains("3")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[1]
            .assertTextContains("2")
        composeRule.onAllNodesWithTag(TestTags.NOTE_ITEM)[2]
            .assertTextContains("1")
    }
}