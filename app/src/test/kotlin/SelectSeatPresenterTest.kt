import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.model.Tier
import woowacourse.movie.selectseat.SelectSeatContract
import woowacourse.movie.selectseat.SelectSeatPresenter
import woowacourse.movie.selectseat.uimodel.PriceUiModel
import woowacourse.movie.selectseat.uimodel.SeatUiModel

class SelectSeatPresenterTest {
    private lateinit var view: SelectSeatContract.View

    private lateinit var presenter: SelectSeatContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<SelectSeatContract.View>()
        presenter = SelectSeatPresenter(view, DummyMovieRepository)
    }

    @Test
    @DisplayName("극장의 좌석 정보를 불러오면 화면에 나타난다")
    fun show_Seat_view_When_load_data_Success() {
        // when
        every { view.initSeats(any()) } just Runs

        // given
        presenter.initSeats(0)

        // then
        verify(exactly = 1) { view.initSeats(any()) }
    }

    @Test
    @DisplayName("선택된 좌석들의 예매 가격을 계산하면 뷰에 반영한다.")
    fun update_view_When_complete_selected_seats_price() {
        // when
        every { view.showPrice(PriceUiModel(15_000)) } just Runs

        // given
        presenter.calculatePrice(listOf(SeatUiModel(1, 1, Tier.S)))

        // then
        verify { view.showPrice(PriceUiModel(15_000)) }
    }
}
