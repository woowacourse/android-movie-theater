package woowacourse.movie.view.setting

import android.Manifest
import android.app.NotificationManager
import android.content.Context
import android.provider.Settings
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
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
import woowacourse.movie.R
import woowacourse.movie.fixture.TestData
import woowacourse.movie.matchers.performClick
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.receiver.NotificationReceiver
import java.time.LocalDateTime

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
    fun 정확한_알람_권한이_없을_경우_설정_창으로_이동한다() {
        // when
        onView(withId(R.id.switch_setting_push_alarm))
            .performClick()
        // then
        intended(hasAction(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM))
    }

    /**
     * 이 테스트는 동작하지 않습니다 !!
     * 특별 권한은 라이브러리 추가 없이 테스트가 불가능하여 일단 보류했습니다
     * PR에 해당 내용 올리겠습니다
     */
    @Test
    fun 푸시_알림이_활성화_된_경우_알림이_온다() {
        // given
        val manager = context.getSystemService(NotificationManager::class.java)
        onView(withId(R.id.switch_setting_push_alarm))
            .performClick()

        // when
        NotificationReceiver.setNotification(
            context,
            TestData.tickets[0],
            LocalDateTime.now(),
        )
        Thread.sleep(1000)

        // then
        val result = manager.activeNotifications.find { it.id == TestData.tickets[0].hashCode() }
        assert(result != null)
    }
}
