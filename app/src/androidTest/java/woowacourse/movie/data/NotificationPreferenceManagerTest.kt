package woowacourse.movie.data

import android.content.Context
import android.content.SharedPreferences
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.preference.NotificationPreferenceManager
import woowacourse.movie.presentation.fixture.fakeContext

class NotificationPreferenceManagerTest {
    private lateinit var prefs: SharedPreferences
    private lateinit var notificationPreferenceManager: NotificationPreferenceManager

    @Before
    fun setUp() {
        prefs = fakeContext.getSharedPreferences("fakePrefs", Context.MODE_PRIVATE)
        notificationPreferenceManager = NotificationPreferenceManager(prefs)
    }

    @Test
    fun `알림_설정_값을_저장하고_불러올_수_있다`() {
        // When
        notificationPreferenceManager.updateNotificationEnabled(true)
        val result = notificationPreferenceManager.isNotificationEnabled()

        // Then
        assertThat(result).isTrue()
    }
}
