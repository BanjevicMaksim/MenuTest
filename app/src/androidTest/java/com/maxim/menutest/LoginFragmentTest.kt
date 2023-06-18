package com.maxim.menutest

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.clearText
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maxim.menutest.idling.waitUntilViewIsDisplayed
import com.maxim.menutest.ui.login.LoginFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class MenuUiTest {

    @Test
    fun test_user_login_error() {
        val navController = mock(NavController::class.java)

        launchFragmentInContainer(themeResId = R.style.Theme_App_Starting) {
            LoginFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.nav_graph)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.etEmail)).perform(clearText(), typeText("test"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.etPassword)).perform(clearText(), typeText("test"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnSignIn)).perform(ViewActions.click())

        waitUntilViewIsDisplayed(withText("That didn't work"))
        onView(withText("That didn't work"))
            .inRoot(isDialog())
            .check(matches(isDisplayed()))
    }

    @Test
    fun test_user_login_success() {
        val navController = mock(NavController::class.java)

        launchFragmentInContainer(themeResId = R.style.Theme_App_Starting) {
            LoginFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.nav_graph)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        onView(withId(R.id.etEmail)).perform(clearText(), typeText("test@testmenu.app"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.etPassword)).perform(clearText(), typeText("test1234"))
        Espresso.closeSoftKeyboard()
        onView(withId(R.id.btnSignIn)).perform(ViewActions.click())
        verify(navController).navigate(R.id.action_loginFragment_to_venuesFragment)
    }
}