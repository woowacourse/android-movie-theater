package woowacourse.movie.booking

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.booking.detail.BookingDetailContract
import woowacourse.movie.booking.detail.BookingDetailPresenter
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Schedule
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel
import woowacourse.movie.util.Formatter.formatDateDotSeparated
import woowacourse.movie.util.Formatter.formatTimeWithMidnight24
import java.time.LocalDate
import java.time.LocalTime
import kotlin.test.assertEquals

class SchedulerDetailPresenterTest {
    private lateinit var presenter: BookingDetailPresenter
    private lateinit var mockView: BookingDetailContract.View
    private lateinit var mockMovie: Movie
    private lateinit var mockMovieUiData: MovieUiModel
    private lateinit var mockTheaterUiData: TheaterUiModel
    private lateinit var mockTicket: Ticket

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockMovie =
            Movie(
                title = "해리 포터와 마법사의 돌",
                imageSource = "harry_potter.png",
                screeningStartDate = LocalDate.of(2028, 10, 1),
                screeningEndDate = LocalDate.of(2028, 10, 25),
                runningTime = 150,
            )

        mockTicket =
            Ticket(
                theater = "선릉",
                title = "해리 포터와 마법사의 돌",
                headCount = HeadCount(2),
                selectedDate = LocalDate.of(2028, 10, 13),
                selectedTime = LocalTime.of(11, 0),
                seats = Seats(emptyList()),
            )

        mockTheaterUiData =
            TheaterUiModel(
                place = "선릉",
                schedule = Schedule(mockMovie, listOf(LocalTime.of(23, 0), LocalTime.of(11, 0))).toUiModel(),
            )

        mockMovieUiData = mockMovie.toUiModel()

        presenter = BookingDetailPresenter(view = mockView)
        presenter.initializeData(mockMovieUiData, mockTheaterUiData)
    }

    @Test
    fun `영화가 주어지면 View에 초기 데이터를 보여준다`() {
        presenter.createDefaultTicket()
        presenter.setUpTicket()

        verify { mockView.showMovieInfo(mockMovieUiData) }
        verify { mockView.showHeadCount() }

        verify { mockView.showScreeningDates(any(), any()) }
        verify { mockView.showScreeningTimes(any(), any()) }
    }

    @Test
    fun `영화가 주어졌을 때 날짜를 선택하면 Ticket에 해당 날짜가 반영되어 화면에 표시된다`() {
        presenter.createDefaultTicket()
        presenter.setUpTicket()

        val selectedDate = LocalDate.of(2028, 10, 13)
        presenter.selectDate(selectedDate)

        verify { mockView.showScreeningTimes(any(), any()) }
    }

    @Test
    fun `+버튼을 누르면 인원수가 0인 경우에 1명씩 추가됨을 화면에 표시한다`() {
        presenter.createDefaultTicket()
        presenter.setUpTicket()

        // 평일임
        val selectedDate = LocalDate.of(2028, 10, 13)
        val selectedTime = LocalTime.of(23, 0)
        presenter.selectDate(selectedDate)
        presenter.selectTime(selectedTime)

        presenter.increaseHeadCount()

        verify { mockView.showHeadCount() }
    }

    @Test
    fun `예매 확인버튼을 누르면 좌석 선택 화면으로 넘어간다`() {
        val selectedDate = LocalDate.of(2028, 10, 13)
        val selectedTime = LocalTime.of(23, 0)
        presenter.createDefaultTicket()
        presenter.setUpTicket()

        presenter.selectDate(selectedDate)
        presenter.selectTime(selectedTime)

        presenter.increaseHeadCount()
        presenter.confirmReservation()

        val formattedDate = formatDateDotSeparated(selectedDate)
        val formattedTime = formatTimeWithMidnight24(selectedTime)

        verify {
            mockView.startSeatSelectionActivity(
                match {
                    it.selectedDateText == formattedDate &&
                        it.selectedTimeText == formattedTime &&
                        it.headCount == 1
                },
            )
        }
    }

    @Test
    fun `인원수가 10명인 경우 -버튼을 누르면 인원수가 줄어든다`() {
        presenter.restoreTicketData(10, "2028.10.13", "11:00")
        presenter.setUpTicket()

        presenter.decreaseHeadCount()
        val currentTicket = presenter.getCurrentTicketUiModel()

        verify { mockView.showHeadCount() }
        assertEquals(currentTicket.headCount, 9)
    }

    @Test
    fun `저장된 인원 수가 있으면 복원된다`() {
        presenter.restoreTicketData(10, "2028.10.13", "11:00")
        presenter.setUpTicket()

        val currentTicket = presenter.getCurrentTicketUiModel()
        verify { mockView.showHeadCount() }

        assertEquals(currentTicket.headCount, 10)
    }
}
