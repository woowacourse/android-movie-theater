import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
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
    fun `선택한_좌석에_맞는_가격을_계산한다`() {
        // given
        presenter.initMaxCount(HeadCount(3))

        // when
        presenter.selectSeat(Position(1, 1))
        presenter.selectSeat(Position(2, 1))
        presenter.selectSeat(Position(3, 1))

        // then
        verify(exactly = 1) { view.showPrice(PriceUiModel(40000)) }
    }

    @Test
    fun `티켓_인원만큼_좌석을_선택하면_확인_버튼을_활성화_시킨다`() {
        // given
        presenter.initMaxCount(HeadCount(3))

        // when
        presenter.selectSeat(Position(1, 1))
        presenter.selectSeat(Position(2, 1))
        presenter.selectSeat(Position(3, 1))

        // then
        verify(exactly = 1) { view.activatePurchase() }
    }

    @Test
    fun `티켓_인원보다_좌석을_많이_선택하면_좌석이_선택되지_않는다`() {
        // given
        presenter.initMaxCount(HeadCount(0))

        // when
        presenter.selectSeat(Position(1, 1))
        presenter.selectSeat(Position(2, 1))
        presenter.selectSeat(Position(3, 1))

        // then
        verify { view.showPrice(PriceUiModel(0)) }
    }
}
