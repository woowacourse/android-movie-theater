package woowacourse.movie.booking

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import woowacourse.movie.booking.detail.BookingDetailContract
import woowacourse.movie.booking.detail.BookingDetailPresenter
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.createMovie
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.ScreeningInfo
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel
import woowacourse.movie.util.Formatter.formatDateDotSeparated
import woowacourse.movie.util.Formatter.formatTimeWithMidnight24
import java.time.LocalDate
import java.time.LocalTime

class BookingDetailPresenterTest {
    private val selectedDate = LocalDate.of(2028, 10, 13)
    private val selectedTime = LocalTime.of(23, 0)
    private lateinit var presenter: BookingDetailPresenter
    private lateinit var mockView: BookingDetailContract.View
    private lateinit var mockMovieUiData: MovieUiModel

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        val mockMovie = createMovie(HARRY_POTTER)

        val mockTheaterUiData =
            TheaterUiModel(
                place = "선릉",
                schedule = ScreeningInfo(mockMovie, listOf(LocalTime.of(23, 0), LocalTime.of(11, 0))).toUiModel(),
            )

        mockMovieUiData = mockMovie.toUiModel()

        presenter = BookingDetailPresenter(view = mockView)
        presenter.initializeData(mockMovieUiData, mockTheaterUiData)
    }

    @Test
    fun `영화가 주어지면 View에 초기 데이터를 보여준다`() {
        // when
        presenter.createDefaultTicket()
        presenter.setUpTicket()

        // then
        verify { mockView.showMovieInfo(mockMovieUiData) }
        verify { mockView.showHeadCount(any()) }
        verify { mockView.showScreeningDates(any(), any()) }
        verify { mockView.showScreeningTimes(any(), any()) }
    }

    @Test
    fun `영화가 주어졌을 때 날짜를 선택하면 Ticket에 해당 날짜가 반영되어 화면에 표시된다`() {
        // given
        presenter.createDefaultTicket()
        presenter.setUpTicket()

        // when
        presenter.selectDate(selectedDate)

        // then
        verify { mockView.showScreeningTimes(any(), any()) }
    }

    @Test
    fun `+ 버튼을 누르면 인원 수가 1명씩 추가됨을 화면에 표시한다`() {
        // given
        presenter.createDefaultTicket()
        presenter.setUpTicket()

        presenter.selectDate(selectedDate)
        presenter.selectTime(selectedTime)

        // when
        presenter.increaseHeadCount()

        // then
        verify { mockView.showHeadCount(any()) }
    }

    @Test
    fun `예매 확인버튼을 누르면 좌석 선택 화면으로 넘어간다`() {
        // given
        presenter.createDefaultTicket()
        presenter.setUpTicket()

        presenter.selectDate(selectedDate)
        presenter.selectTime(selectedTime)

        presenter.increaseHeadCount()

        // when
        presenter.confirmReservation()

        val formattedDate = formatDateDotSeparated(selectedDate)
        val formattedTime = formatTimeWithMidnight24(selectedTime)

        // then
        verify {
            mockView.startSeatSelectionActivity(
                match {
                    it.selectedDateText == formattedDate &&
                        it.selectedTimeText == formattedTime &&
                        it.headCount == 2
                },
            )
        }
    }

    @ParameterizedTest
    @ValueSource(ints = [2, 3, 100, 200])
    fun `인원수가 2명 이상인 경우 - 버튼을 누르면 인원수가 줄어든다`(count: Int) {
        // given
        presenter.restoreTicketData(count, "2028-10-13", "11:00")
        presenter.setUpTicket()

        // when
        presenter.decreaseHeadCount()

        // then
        verify { mockView.showHeadCount(count - 1) }
    }

    @Test
    fun `저장된 인원 수가 있으면 복원된다`() {
        // given & when
        presenter.restoreTicketData(10, "2028-10-13", "11:00")
        presenter.setUpTicket()

        // then
        verify { mockView.showHeadCount(10) }
    }
}
