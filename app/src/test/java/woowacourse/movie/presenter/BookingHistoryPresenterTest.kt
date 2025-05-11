package woowacourse.movie.presenter

import android.content.Context
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDate
import woowacourse.movie.domain.model.MovieTime
import woowacourse.movie.feature.bookinghistory.contract.BookingHistoryContract
import woowacourse.movie.feature.bookinghistory.presenter.BookingHistoryPresenter
import woowacourse.movie.feature.mapper.toUi
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.model.MovieUiModel

class BookingHistoryPresenterTest {
    private lateinit var presenter: BookingHistoryPresenter
    private lateinit var view: BookingHistoryContract.View
    private lateinit var movieUiModel: MovieUiModel
    private lateinit var bookingInfoUiModel: BookingInfoUiModel

    @BeforeEach
    fun setup() {
        view = mockk(relaxed = true)
        val mockContext = mockk<Context>(relaxed = true)
        presenter = BookingHistoryPresenter(mockContext, view)

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
    }

    @Test
    fun selectBookingHistory는_선택된_예매_정보를_상세_화면으로_전달한다() {
        // when
        presenter.selectBookingHistory(bookingInfoUiModel)

        // then
        verify { view.navigateToBookingDetail(bookingInfoUiModel) }
    }
}
