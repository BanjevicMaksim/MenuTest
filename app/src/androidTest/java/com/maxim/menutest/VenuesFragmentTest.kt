package com.maxim.menutest

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.maxim.menutest.assertion.RecyclerViewItemCountAssertion
import com.maxim.menutest.idling.waitUntilViewIsDisplayed
import com.maxim.menutest.ui.venue.VenuesFragment
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito


@RunWith(AndroidJUnit4::class)
class VenuesFragmentTest {

    @Test
    fun test_venue_list_not_empty() {
        val navController = Mockito.mock(NavController::class.java)

        launchFragmentInContainer(themeResId = R.style.Theme_MenuTest) {
            VenuesFragment().also { fragment ->
                fragment.viewLifecycleOwnerLiveData.observeForever { viewLifecycleOwner ->
                    if (viewLifecycleOwner != null) {
                        navController.setGraph(R.navigation.nav_graph)
                        Navigation.setViewNavController(fragment.requireView(), navController)
                    }
                }
            }
        }

        waitUntilViewIsDisplayed(ViewMatchers.withId(R.id.rvVenues))
        onView(ViewMatchers.withId(R.id.rvVenues)).check(RecyclerViewItemCountAssertion(3))
    }
}

