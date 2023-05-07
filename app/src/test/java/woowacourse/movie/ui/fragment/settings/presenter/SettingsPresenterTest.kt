package woowacourse.movie.ui.fragment.settings.presenter

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.storage.SettingsStorage
import woowacourse.movie.ui.fragment.settings.SettingsContract

class SettingsPresenterTest {
    private lateinit var presenter: SettingsPresenter
    private lateinit var settingsStorage: SettingsStorage
    private lateinit var view: SettingsContract.View

    @Before
    fun setUp() {
        settingsStorage = mockk()
        view = mockk()
        presenter = SettingsPresenter(settingsStorage, view)
    }

    @Test
    fun checkPushNotificationState() {
        // given
        every { settingsStorage.enablePushNotification } returns true
        justRun { view.setPushNotificationState(any()) }

        // when
        presenter.checkPushNotificationState()

        // then
        verify {
            view.setPushNotificationState(any())
        }
    }
}
