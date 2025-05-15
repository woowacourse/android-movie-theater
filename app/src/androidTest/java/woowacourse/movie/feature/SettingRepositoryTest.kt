package woowacourse.movie.feature

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.datasource.SettingPreferenceDataSource
import woowacourse.movie.data.repository.SettingRepositoryImpl
import woowacourse.movie.domain.repository.SettingRepository

@Suppress("ktlint:standard:function-naming")
class SettingRepositoryTest {
    private lateinit var repository: SettingRepository

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        repository = SettingRepositoryImpl(SettingPreferenceDataSource(context))
    }

    @Test
    fun 알림_설정을_저장하고_불러올_수_있다() {
        val expected1 = false
        repository.saveNotificationSetting(expected1)

        val actual1 = repository.fetchNotificationSetting()
        assertThat(actual1).isEqualTo(expected1)

        val expected2 = true
        repository.saveNotificationSetting(expected2)

        val actual2 = repository.fetchNotificationSetting()
        assertThat(actual2).isEqualTo(expected2)
    }
}
