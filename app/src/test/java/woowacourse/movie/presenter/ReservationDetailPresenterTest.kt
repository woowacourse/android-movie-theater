package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import io.mockk.verifySequence
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.dummyTheaterUIModel
import woowacourse.movie.view.reservation.detail.ReservationDetailContract
import woowacourse.movie.view.reservation.detail.ReservationDetailPresenter
import java.time.LocalDate

class ReservationDetailPresenterTest {
    private lateinit var presenter: ReservationDetailContract.Presenter
    private lateinit var view: ReservationDetailContract.View

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = ReservationDetailPresenter(view)
    }

    @Test
    fun `데이터를 가져오면 영화 정보를 보여준다`() {
        // given
        every { view.showMovieInfo(any()) } just Runs

        // when: 영화 목록을 조회하면
        presenter.fetchData(dummyTheaterUIModel)

        // then: 영화 정보를 설정한다
        verify { view.showMovieInfo(any()) }
    }

    @Test
    fun `영화 정보를 불러오지 못하는 경우 다이얼로그를 보여준다`() {
        every { view.showErrorDialog() } just Runs

        presenter.fetchData(null)

        verify { view.showErrorDialog() }
    }

    @Test
    fun `날짜 스피너에서 날짜를 선택하면 해당 날짜의 시간 목록을 보여준다`() {
        // given
        val timetableSlot = slot<List<String>>()
        val now = LocalDate.of(2025, 4, 25)
        every { view.showMovieInfo(any()) } just Runs
        every { view.updateTimeAdapter(capture(timetableSlot)) } just Runs

        // when
        presenter.fetchData(dummyTheaterUIModel)
        presenter.selectDate(now)

        // then
        verifySequence {
            view.showMovieInfo(any())
            view.updateTimeAdapter(any())
        }

        val expected =
            listOf("11:00", "14:00", "17:00", "20:00")
        assertThat(timetableSlot.captured).isEqualTo(expected)
    }

    @Test
    fun `티켓 개수를 증가시키면 뷰에 반영된다`() {
        val countSlot = slot<Int>()

        every { view.showMovieInfo(any()) } just Runs
        every { view.showTicketCount(capture(countSlot)) } just Runs

        presenter.fetchData(dummyTheaterUIModel)
        presenter.plusTicketCount()

        verify { view.showTicketCount(any()) }
        assertThat(countSlot.captured).isEqualTo(2)
    }

    @Test
    fun `티켓 개수를 감소시키면 뷰에 반영된다`() {
        val countSlot = slot<Int>()

        every { view.showMovieInfo(any()) } just Runs
        every { view.showTicketCount(capture(countSlot)) } just Runs

        presenter.fetchData(dummyTheaterUIModel)
        presenter.plusTicketCount()
        presenter.minusTicketCount()

        verify { view.showTicketCount(any()) }

        assertThat(countSlot.captured).isEqualTo(1)
    }
}
