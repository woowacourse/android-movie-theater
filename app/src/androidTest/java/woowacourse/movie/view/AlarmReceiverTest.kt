package woowacourse.movie.view

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.data.SharedPreferencesStore
import woowacourse.movie.view.receiver.AlarmReceiver

@RunWith(AndroidJUnit4::class)
class AlarmReceiverTest {
    private lateinit var context: Context
    private lateinit var receiver: AlarmReceiver
    private lateinit var intent: Intent
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesStore: SharedPreferencesStore

    private var originalNotificationPermission: Boolean = false

    @Before
    fun setUp() {
        context = InstrumentationRegistry.getInstrumentation().targetContext
        intent = AlarmReceiver.newIntent(context, "해리포터와 비밀의 방", 1L)

        sharedPreferences = context.getSharedPreferences("AppPreferences", Context.MODE_PRIVATE)
        sharedPreferencesStore = SharedPreferencesStore(context)

        originalNotificationPermission = sharedPreferencesStore.notificationPermission()

        receiver = AlarmReceiver()
    }

    @Test
    fun `sharedPreferences에_권한이_거부되어_있으면_알람을_생성하지_않는다`() {
        // given
        sharedPreferences.edit().putBoolean("notification_permission", false).apply()
        receiver.onReceive(context, intent)

        val manager = context.getSystemService(NotificationManager::class.java)
        val result = manager.activeNotifications.find { it.id.toLong() == 1L }

        assertThat(result).isNull()
    }

    @Test
    fun `sharedPreferences에_권한이_허용_되어_있으면_알람을_생성한다`() {
        // given
        sharedPreferences.edit().putBoolean("notification_permission", true).apply()
        receiver.onReceive(context, intent)

        val manager = context.getSystemService(NotificationManager::class.java)
        val result = manager.activeNotifications.find { it.id == 1 }

        assertThat(result).isNotNull()
    }

    @After
    fun tearDown() {
        sharedPreferences.edit()
            .putBoolean("notification_permission", originalNotificationPermission).apply()
        val manager = context.getSystemService(NotificationManager::class.java)
        manager.cancelAll()
    }
}
