package woowacourse.movie.setting

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.setting.SettingContract
import woowacourse.movie.presenter.setting.SettingPresenter
import woowacourse.movie.ui.storage.PushNotificationRepository

class SettingPresenterTest {
    private lateinit var presenter: SettingContract.Present
    private lateinit var view: SettingContract.View
    private lateinit var pushNotificationRepository: PushNotificationRepository

    @Before
    fun setUp() {
        view = mockk()
        pushNotificationRepository = mockk()
        presenter = SettingPresenter(view, pushNotificationRepository)
    }

    @Test
    fun `현재 알림 수신 여부를 뷰를 통해 보여준다`() {
        // given
        every { pushNotificationRepository.getPushNotification() } returns true
        val slot = slot<Boolean>()
        justRun { view.updateNotificationState(capture(slot)) }

        // when
        presenter.setupNotificationState()

        // then
        val actual = slot.captured
        val expected = true
        assertEquals(expected, actual)
        verify { pushNotificationRepository.getPushNotification() }
        verify { view.updateNotificationState(actual) }
    }
}
