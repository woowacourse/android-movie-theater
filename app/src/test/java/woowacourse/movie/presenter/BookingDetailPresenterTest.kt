package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.feature.bookingdetail.contract.BookingDetailContract
import woowacourse.movie.feature.bookingdetail.presenter.BookingDetailPresenter
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieUiModel

class BookingDetailPresenterTest {
    private lateinit var presenter: BookingDetailPresenter
    private lateinit var view: BookingDetailContract.View

    private lateinit var movieUiModel: MovieUiModel
    private lateinit var bookingInfoUiModel: BookingInfoUiModel
    private lateinit var theaterName: TheaterName

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = BookingDetailPresenter(view)

        movieUiModel =
            Movie(
                title = "레디 플레이어 원",
                startDate = MovieDate(2025, 5, 1),
                endDate = MovieDate(2025, 5, 10),
                runningTime = 148,
                availableTheaters =
                    listOf(
                        TheaterName("선릉", listOf(MovieTime(9, 0), MovieTime(12, 0), MovieTime(15, 0))),
                        TheaterName("잠실", listOf(MovieTime(10, 0), MovieTime(13, 0))),
                        TheaterName("강남", listOf(MovieTime(11, 0), MovieTime(14, 0), MovieTime(17, 0), MovieTime(20, 0))),
                    ),
            ).toUi()

        bookingInfoUiModel =
            BookingInfoUiModel(
                movie = movieUiModel,
                date = movieUiModel.startDate,
                movieTime = MovieTime(10, 0).toUi(),
            )

        theaterName = TheaterName(times = listOf(MovieTime(10, 0)))
    }

    @Test
    fun `onCreateView 호출 시 날짜, 시간, 예약정보를 갱신한다`() {
        // given & when
        presenter.prepareBookingInfo(movieUiModel, theaterName)
        val dates = slot<List<MovieDateUiModel>>()
        val times = slot<List<String>>()
        val bookingInfo = slot<BookingInfoUiModel>()

        // then
        verify {
            view.setupDateView(capture(dates))
            view.setupTimeView(capture(times))
            view.updateView(capture(bookingInfo))
        }

        assertThat(dates.captured.first()).isEqualTo(movieUiModel.startDate)
        assertThat(times.captured.first()).isEqualTo("10:00")
        assertThat(bookingInfo.captured).isEqualTo(bookingInfoUiModel)
    }

    @Test
    fun `onTicketCountIncreased 호출 시 티켓 수 증가 후 뷰의 출력을 갱신한다`() {
        // given
        val ticketCount = slot<Int>()
        presenter.prepareBookingInfo(movieUiModel, theaterName)

        // when
        presenter.increaseTicketCount()

        // then
        verify { view.updateTicketCount(capture(ticketCount)) }
        assertThat(ticketCount.captured).isEqualTo(2)
    }

    @Test
    fun `onTicketCountDecreased 호출 시 티켓 수 감소 후 뷰의 출력을 갱신한다`() {
        // given
        val ticketCount = slot<Int>()
        presenter.prepareBookingInfo(movieUiModel, theaterName)

        // when
        presenter.decreaseTicketCount()

        // then
        verify { view.updateTicketCount(capture(ticketCount)) }
        assertThat(ticketCount.captured).isEqualTo(1)
    }

    @Test
    fun `onBookingCompleteButtonClicked 호출 시 좌석 선택 화면으로 이동한다`() {
        // given
        val bookingInfo = slot<BookingInfoUiModel>()
        presenter.prepareBookingInfo(movieUiModel, theaterName)

        // when
        presenter.confirmBookingInfo()

        // then
        verify { view.navigateToBookingSeat(capture(bookingInfo)) }
        assertThat(bookingInfo.captured).isEqualTo(bookingInfoUiModel)
    }

    @Test
    fun `onBackButtonClicked 호출 시 뒤로 이동한다`() {
        // given & when
        presenter.onBackButtonClicked()

        // then
        verify { view.navigateToBack() }
    }

    @Test
    fun `onSaveInstanceState 호출 시 현재 예약정보를 반환한다`() {
        // given
        presenter.prepareBookingInfo(movieUiModel, theaterName)

        // when
        val savedState = presenter.saveBookingInfo()

        // then
        assertThat(savedState.movie).isEqualTo(movieUiModel)
    }

    @Test
    fun `onRestoreInstanceState 호출 시 기존에 저장된 예약정보로 복원한다`() {
        // given
        presenter.prepareBookingInfo(movieUiModel, theaterName)

        // when
        presenter.loadBookingInfo(bookingInfoUiModel)

        // then
        verify { view.updateView(bookingInfoUiModel) }
    }
}
