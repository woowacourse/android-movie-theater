package woowacourse.movie.view.setting

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import org.hamcrest.CoreMatchers.not
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.MovieTheaterApplication
import woowacourse.movie.R
import woowacourse.movie.fixture.TestData
import woowacourse.movie.matchers.performClick
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.extension.notificationManager

@RunWith(AndroidJUnit4::class)
@Suppress("FunctionName")
class SettingFragmentTest {
    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @get:Rule
    val permissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            Manifest.permission.POST_NOTIFICATIONS,
        )

    val context: Context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setUp() {
        Intents.init()
        val repository =
            (context.applicationContext as MovieTheaterApplication)
                .repositoryProvider.ticketRepository
        repository.save(TestData.tickets[0]) {}

        onView(withId(R.id.settings))
            .performClick()
    }

    @After
    fun after() {
        Intents.release()
    }

    @Test
    fun 푸시_알림이_비활성화_된_경우_푸시_알림이_오지_않는다() {
        // given
        lateinit var fragment: SettingFragment
        scenarioRule.scenario.onActivity { activity ->
            fragment =
                activity.supportFragmentManager.findFragmentById(
                    R.id.fragment_container_main,
                ) as SettingFragment
        }
        val manager = context.getSystemService(NotificationManager::class.java)

        // when
        fragment.setPermissionSwitch(false)

        // then
        val result1 = manager.activeNotifications.find { it.id == TestData.tickets[0].hashCode() }
        assert(result1 == null)
    }

    @Test
    fun 푸시_알림이_활성화_된_경우_알림이_온다() {
        // given
        lateinit var fragment: SettingFragment
        scenarioRule.scenario.onActivity { activity ->
            fragment =
                activity.supportFragmentManager.findFragmentById(
                    R.id.fragment_container_main,
                ) as SettingFragment
        }
        val manager = context.notificationManager()

        // when
        fragment.setPermissionSwitch(true)
        Thread.sleep(10000)

        // then
        val result = manager.activeNotifications.size
        assert(result > 0)
    }
}
