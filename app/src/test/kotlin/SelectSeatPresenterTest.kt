import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.model.HeadCount
import woowacourse.movie.selectseat.SelectSeatContract
import woowacourse.movie.selectseat.SelectSeatPresenter
import woowacourse.movie.selectseat.uimodel.Position
import woowacourse.movie.selectseat.uimodel.PriceUiModel

@ExtendWith(MockKExtension::class)
class SelectSeatPresenterTest {
    @RelaxedMockK
    private lateinit var view: SelectSeatContract.View

    private lateinit var presenter: SelectSeatContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = SelectSeatPresenter(view, DummyMovieRepository)
        presenter.initSeats(0)
    }

    @Test
    @DisplayName("선택한 좌석에 맞는 가격을 계산한다")
    fun update_view_When_complete_selected_seats_price() {
        // given
        every { view.showPrice(any()) } just Runs
        presenter.initMaxCount(HeadCount(3))

        // when
        presenter.selectSeat(Position(1, 1))
        presenter.selectSeat(Position(2, 1))
        presenter.selectSeat(Position(3, 1))

        // then
        verify(exactly = 1) { view.showPrice(PriceUiModel(40000)) }
    }
}
