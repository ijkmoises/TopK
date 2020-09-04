package br.com.emesistemas.topk.presentation.ui.fragments

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import br.com.emesistemas.topk.BuildConfig
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.presentation.ui.activities.MainActivity
import br.com.emesistemas.topk.presentation.ui.adapters.RepoListAdapter
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RepoDetailFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun setup() {
        BuildConfig.IS_UI_TESTING.set(true)
    }

    @After
    fun tearDown() {
        BuildConfig.IS_UI_TESTING.set(false)
        InstrumentationRegistry.getInstrumentation()
            .targetContext.deleteDatabase(BuildConfig.DATABASENAME_TEST)
    }

    @Test
    fun check() {
        onView(withId(R.id.rvRepoList))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<RepoListAdapter.RepoViewHolder>(0, click())
            )
    }

    @Test
    fun test_Toolbar_hasHomeAsUpButton() {
        onView(
            Matchers.allOf(
                withId(R.id.toolbar)
                , withId(android.R.id.home)
                , ViewMatchers.isDisplayed()
            )
        )
    }
}