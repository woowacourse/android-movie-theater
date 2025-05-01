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
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.feature.bookingdetail.contract.BookingDetailContract
import woowacourse.movie.feature.bookingdetail.presenter.BookingDetailPresenter
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieDateUiModel
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.ScreeningUiModel

class BookingDetailPresenterTest {
    private lateinit var presenter: BookingDetailPresenter
    private lateinit var view: BookingDetailContract.View

    private lateinit var movieUiModel: MovieUiModel
    private lateinit var screeningUiModel: ScreeningUiModel
    private lateinit var bookingInfoUiModel: BookingInfoUiModel

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
            ).toUi()

        bookingInfoUiModel =
            BookingInfoUiModel(
                movie = movieUiModel,
                theaterName = "혜화",
                date = movieUiModel.startDate,
                movieTime = MovieTime(10, 0).toUi(),
            )

        screeningUiModel =
            Screening(
                Movie(
                    title = "레디 플레이어 원",
                    startDate = MovieDate(2025, 5, 1),
                    endDate = MovieDate(2025, 5, 10),
                    runningTime = 148,
                ),
                "혜화",
                listOf(MovieTime(10, 0)),
            ).toUi()
    }

    @Test
    fun `onCreateView 호출 시 날짜, 시간, 예약정보를 갱신한다`() {
        // given & when
        presenter.prepareBookingInfo(screeningUiModel)
        val dates = slot<List<MovieDateUiModel>>()
        val times = slot<List<String>>()
        val bookingInfo = slot<BookingInfoUiModel>()

        // then
        verify {
            view.setupDateView(capture(dates))
            view.setupTimeView(capture(times))
            view.updateBookingInfo(capture(bookingInfo))
        }

        assertThat(dates.captured.first()).isEqualTo(movieUiModel.startDate)
        assertThat(times.captured.first()).isEqualTo("10:00")
        assertThat(bookingInfo.captured).isEqualTo(bookingInfoUiModel)
    }

    @Test
    fun `onTicketCountIncreased 호출 시 티켓 수 증가 후 뷰의 출력을 갱신한다`() {
        // given
        presenter.prepareBookingInfo(screeningUiModel)

        // when
        presenter.increaseTicketCount()

        // then
        verify { view.updateBookingInfo(any()) }
    }

    @Test
    fun `onTicketCountDecreased 호출 시 티켓 수 감소 후 뷰의 출력을 갱신한다`() {
        // given
        presenter.prepareBookingInfo(screeningUiModel)

        // when
        presenter.decreaseTicketCount()

        // then
        verify { view.updateBookingInfo(any()) }
    }

    @Test
    fun `onBookingCompleteButtonClicked 호출 시 좌석 선택 화면으로 이동한다`() {
        // given
        val bookingInfo = slot<BookingInfoUiModel>()
        presenter.prepareBookingInfo(screeningUiModel)

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
        presenter.prepareBookingInfo(screeningUiModel)

        // when
        val savedState = presenter.saveBookingInfo()

        // then
        assertThat(savedState.movie).isEqualTo(movieUiModel)
    }

    @Test
    fun `onRestoreInstanceState 호출 시 기존에 저장된 예약정보로 복원한다`() {
        // given
        presenter.prepareBookingInfo(screeningUiModel)

        // when
        presenter.loadBookingInfo(bookingInfoUiModel)

        // then
        verify { view.updateBookingInfo(bookingInfoUiModel) }
    }
}
