package woowacourse.movie.presenter.setting

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.TestData
import woowacourse.movie.repository.TicketRepository
import woowacourse.movie.view.setting.SettingContract
import woowacourse.movie.view.setting.SettingPresenter

class SettingPresenterTest {
    private lateinit var presenter: SettingPresenter
    private lateinit var view: SettingContract.View
    private lateinit var repository: TicketRepository

    @BeforeEach
    fun setUp() {
        view = mockk()
        repository = mockk()
        presenter = SettingPresenter(view, repository)
    }

    @Test
    fun `영화 얘매 시간 30분 전에 알람을 세팅한다`() {
        // given
        val ticketList = listOf(TestData.ticket)
        val notificationTime = listOf(TestData.ticket.showTime.minusMinutes(30))
        every { view.setNotification(any(), any()) } just Runs
        every { repository.findAll() } returns Result.success(ticketList)

        // when
        presenter.setNotification()

        // then
        verify {
            view.setNotification(ticketList, notificationTime)
        }
    }
}
