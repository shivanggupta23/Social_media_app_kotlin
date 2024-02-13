package com.example.news_app

//import androidx.compose.ui.test.*
//import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    // Initialize the Compose testing rule
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun createComposeRule(): Any {
        TODO("Not yet implemented")
    }

    @Before
    fun setUp() {
        // Add any setup logic here
    }

    @Test
    fun testMainActivityLaunch() {
        // Verify that the MainActivity is launched successfully
        composeTestRule.onNodeWithTag("MainActivityTag").assertIsDisplayed()
    }

    @Test
    @Composable
    fun testNavigationToSignUpScreen() {
        // Verify navigation to SignUpScreen
        val navController = rememberNavController()
        composeTestRule.setContent {
            MainActivity()
        }

        // Trigger the navigation action to SignUpScreen
        composeTestRule.onNodeWithText("Sign Up").performClick()

        // Verify that SignUpScreen is displayed
        composeTestRule.onNodeWithTag("SignUpScreenTag").assertIsDisplayed()
    }

    @Test
    @Composable
    fun testNavigationToSignInScreen() {
        // Verify navigation to SignInScreen
        val navController = rememberNavController()
        composeTestRule.setContent {
            MainActivity()
        }

        // Trigger the navigation action to SignInScreen
        composeTestRule.onNodeWithText("Sign In").performClick()

        // Verify that SignInScreen is displayed
        composeTestRule.onNodeWithTag("SignInScreenTag").assertIsDisplayed()
    }

    @Test
    @Composable
    fun testNavigationToDashboard() {
        // Verify navigation to Dashboard
        val navController = rememberNavController()
        composeTestRule.setContent {
            MainActivity()
        }

        // Trigger the navigation action to Dashboard
        composeTestRule.onNodeWithText("Dashboard").performClick()

        // Verify that Dashboard is displayed
        composeTestRule.onNodeWithTag("DashboardTag").assertIsDisplayed()
    }
}

