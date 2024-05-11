package woowacourse.movie.presentation.ui.main.setting

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.repository.PreferenceRepository
import woowacourse.movie.presentation.model.message.NotificationMessageType.*

@ExtendWith(MockKExtension::class)
class SettingPresenterTest {
    @MockK
    private lateinit var view: SettingContract.View

    @MockK
    private lateinit var repository: PreferenceRepository

    @InjectMockKs
    private lateinit var presenter: SettingPresenter

    @Test
    fun `SettingPresenter가 loadNotificationMode()을 했을 때, view에게 mode를 전달한다`() {
        // given
        val mode = true
        every { repository.getNotificationMode() } returns Result.success(mode)
        every { view.showNotificationMode(mode) } just runs

        // when
        presenter.loadNotificationMode()

        // then
        verify { view.showNotificationMode(mode) }
    }

    @Test
    fun `SettingPresenter가 loadNotificationMode()을 했을 때, 예외가 발생하면 view에게 예외를 전달한다`() {
        // given
        val exception = Exception()
        every { repository.getNotificationMode() } returns Result.failure(exception)
        every { view.showSnackBar(e = exception) } just runs

        // when
        presenter.loadNotificationMode()

        // then
        verify { view.showSnackBar(exception) }
    }

    @Test
    fun `SettingPresenter가 changeNotificationMode()을 했을 때, mode가 true라면 view에게 NotificationSuccessMessage를 전달한다`() {
        // given
        val mode = true
        val message = NotificationSuccessMessage
        every { repository.saveNotificationMode(any()) } returns Result.success(Unit)
        every { view.showNotificationMode(mode) } just runs
        every { view.showSnackBar(message) } just runs
        every { view.checkNotificationPermissions(!mode) } just runs

        // when
        presenter.saveNotificationMode(mode)
        presenter.changeNotificationMode(mode)

        // then
        verify { view.showNotificationMode(mode) }
        verify { view.showSnackBar(message) }
    }

    @Test
    fun `SettingPresenter가 changeNotificationMode()을 했을 때, mode가 false라면 view에게 NotificationFailureMessage 전달한다`() {
        // given
        val mode = false
        val message = NotificationFailureMessage
        every { repository.saveNotificationMode(any()) } returns Result.success(Unit)
        every { view.showNotificationMode(mode) } just runs
        every { view.showSnackBar(message) } just runs
        every { view.checkNotificationPermissions(!mode) } just runs

        // when
        presenter.saveNotificationMode(mode)
        presenter.changeNotificationMode(mode)

        // then
        verify { view.showNotificationMode(mode) }
        verify { view.showSnackBar(message) }
    }

    @Test
    fun `SettingPresenter가 changeNotificationMode()을 했을 때, 예외가 발생하면 view에게 예외를 전달한다`() {
        // given
        val exception = Exception()
        val mode = true
        every { repository.saveNotificationMode(any()) } returns Result.failure(exception)
        every { view.showNotificationMode(mode) } just runs
        every { view.showSnackBar(exception) } just runs
        every { view.checkNotificationPermissions(!mode) } just runs

        // when
        presenter.saveNotificationMode(mode)
        presenter.changeNotificationMode(mode)

        // then
        verify { view.showSnackBar(exception) }
    }
}
