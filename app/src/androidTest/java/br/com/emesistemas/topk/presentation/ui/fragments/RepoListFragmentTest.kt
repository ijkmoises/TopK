package br.com.emesistemas.topk.presentation.ui.fragments

import EspressoIdlingResourceRule
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.emesistemas.topk.BuildConfig
import br.com.emesistemas.topk.R
import br.com.emesistemas.topk.mathers.ViewMatcher.matchesInPosition
import br.com.emesistemas.topk.presentation.ui.activities.MainActivity
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class RepoListFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @get: Rule
    val espressoIdlingResourceRule = EspressoIdlingResourceRule()

    @Before
    fun setup() {
        println("moises ramos setup ")
        BuildConfig.IS_UI_TESTING.set(true)
    }

    fun tearDown() {
        println("moises ramos tearDown ")
        BuildConfig.IS_UI_TESTING.set(false)
        InstrumentationRegistry.getInstrumentation()
            .targetContext.deleteDatabase(BuildConfig.DATABASENAME_TEST)
    }

    @Test
    fun isRecyclerViewVisible_onAppLaunch() {
        println("moises ramos teste frag " + BuildConfig.IS_UI_TESTING.get())
        onView(withId(R.id.rvRepoList))
            .check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewHasThreeItemsVisibles_With_AuthorName_And_RepoName_And_Forks_And_Stars() {
        onView(withId(R.id.rvRepoList))
            .check(
                matches(
                    matchesInPosition(
                        position = 0
                        , verifyAuthorName = "google"
                        , verifyForksCount = 6098
                        , verifyRepoName = "iosched"
                        , verifyStarsCount = 20098
                    )
                )
            )

        onView(withId(R.id.rvRepoList))
            .check(
                matches(
                    matchesInPosition(
                        position = 1
                        , verifyAuthorName = "android"
                        , verifyForksCount = 6270
                        , verifyRepoName = "architecture-components-samples"
                        , verifyStarsCount = 18171
                    )
                )
            )

        onView(withId(R.id.rvRepoList))
            .check(
                matches(
                    matchesInPosition(
                        position = 2
                        , verifyAuthorName = "afollestad"
                        , verifyForksCount = 3042
                        , verifyRepoName = "material-dialogs"
                        , verifyStarsCount = 18071
                    )
                )
            )
    }

    @Test
    fun toolbar_hasTitle() {
        onView(
            allOf(
                withId(R.id.toolbar)
                , withText("Top repositórios Kotlin")
                , isDisplayed()
            )
        )
    }

    @Test
    fun toolbar_notHasHomeAsUpButton() {
        onView(
            allOf(
                withId(R.id.toolbar)
                , withId(android.R.id.home)
                , not(isDisplayed())
            )
        )
    }

}