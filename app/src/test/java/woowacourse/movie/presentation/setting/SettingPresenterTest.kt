package woowacourse.movie.presentation.setting

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verifyAll
import io.mockk.verifySequence
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.SettingRepository

class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var repository: SettingRepository
    private lateinit var presenter: SettingPresenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        repository = mockk()
    }

    @Test
    fun `초기화 시 알림 수신 여부를 View에 전달한다`() {
        // given
        every { repository.isNotificationEnabled() } returns true

        // when
        presenter = SettingPresenter(view, repository)

        // then
        verifyAll {
            repository.isNotificationEnabled()
            view.notifyNotificationEnabled(true)
        }
    }

    @Test
    fun `알림 설정을 변경하면 알림 수신 여부가 저장되고 View에 반영된다`() {
        // given
        every { repository.updateNotificationEnabled(any()) } just Runs
        every { repository.isNotificationEnabled() } returns true

        presenter = SettingPresenter(view, repository)

        // when
        presenter.updateNotificationEnabled(true)

        // then
        verifySequence {
            repository.isNotificationEnabled()
            view.notifyNotificationEnabled(true)
            repository.updateNotificationEnabled(true)
            repository.isNotificationEnabled()
            view.notifyNotificationEnabled(true)
        }
    }
}
