import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.model.SeatRate
import woowacourse.movie.selectseat.SelectSeatContract
import woowacourse.movie.selectseat.SelectSeatPresenter
import woowacourse.movie.selectseat.toSeatsUiModel
import woowacourse.movie.selectseat.uimodel.SeatUiModel
import woowacourse.movie.selectseat.uimodel.SelectState

class SelectSeatPresenterTest {
    private lateinit var view: SelectSeatContract.View

    private lateinit var presenter: SelectSeatContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<SelectSeatContract.View>(relaxed = true)
        presenter = SelectSeatPresenter(view, DummyMovies)
    }

    @Test
    @DisplayName("극장의 좌석 정보를 불러오는데 성공하면, SeatUiModel로 정제해서 뷰에게 전달해준다.")
    fun deliver_Seat_to_view_When_load_data_Success() {
        // when
        val expectedTheater = MovieTheater.STUB_A
        val expectedSeats = expectedTheater.seats.toSeatsUiModel()
        every { view.showSeat(expectedSeats) } just Runs

        // given
        presenter.loadInitData(0, 3)

        // then
        verify(exactly = 1) { view.showSeat(expectedSeats) }
    }

    @Test
    @DisplayName("예매 인원보다 좌석을 더 많이 선택할 경우, EXCEED를 뷰에게 전달한다.")
    fun deliver_EXCEED_When_select_seat_more_than_headCount() {
        // when
        val expectedSelectSeats1 = listOf(selectedSeat1)
        val expectedSelectSeats2 = listOf(selectedSeat1, selectedSeat2)

        every { view.showSeat(any()) } just Runs
        every { view.updatePrice(any()) } just Runs
        every { view.updateSeatState(expectedSelectSeats1, SelectState.SUCCESS) } just Runs
        every { view.updateSeatState(expectedSelectSeats2, SelectState.EXCEED) } just Runs
        presenter.loadInitData(0, 1)

        // given
        presenter.changeSeatState(selectedSeat1)
        presenter.changeSeatState(selectedSeat2)

        // then
        verify(exactly = 1) {
            view.updateSeatState(expectedSelectSeats2, SelectState.EXCEED)
        }

        verifyOrder {
            view.updateSeatState(expectedSelectSeats1, SelectState.SUCCESS)
            view.updateSeatState(expectedSelectSeats2, SelectState.EXCEED)
        }
    }

    @Test
    @DisplayName("예매 인원보다 좌석을 적게 선택할 경우, LESS를 뷰에게 전달한다.")
    fun deliver_LESS_When_select_seat_more_than_headCount() {
        // when
        val expectedSelectSeats1 = listOf(selectedSeat1)

        every { view.showSeat(any()) } just Runs
        every { view.updatePrice(any()) } just Runs
        every { view.updateSeatState(expectedSelectSeats1, SelectState.LESS) } just Runs
        presenter.loadInitData(0, 2)

        // given
        presenter.changeSeatState(selectedSeat1)

        // then
        verify(exactly = 1) {
            view.updateSeatState(expectedSelectSeats1, SelectState.LESS)
        }
    }

    @Test
    @DisplayName("예매 인원에 알맞게 좌석을 선택할 경우, SUCCESS를 뷰에게 전달한다.")
    fun deliver_SUCCESS_When_select_seat_more_than_headCount() {
        // when
        val expectedSelectSeats1 = listOf(selectedSeat1)
        val expectedSelectSeats2 = listOf(selectedSeat1, selectedSeat2)
        val expectedSelectSeats3 = listOf(selectedSeat1, selectedSeat2, selectedSeat3)

        every { view.showSeat(any()) } just Runs
        every { view.updatePrice(any()) } just Runs
        every { view.updateSeatState(expectedSelectSeats1, SelectState.LESS) } just Runs
        every { view.updateSeatState(expectedSelectSeats2, SelectState.SUCCESS) } just Runs
        every { view.updateSeatState(expectedSelectSeats3, SelectState.EXCEED) } just Runs
        presenter.loadInitData(0, 2)

        // given
        presenter.changeSeatState(selectedSeat1)
        presenter.changeSeatState(selectedSeat2)

        // then
        verify(exactly = 1) {
            view.updateSeatState(expectedSelectSeats2, SelectState.SUCCESS)
        }

        presenter.changeSeatState(selectedSeat3)
        verifyOrder {
            view.updateSeatState(expectedSelectSeats1, SelectState.LESS)
            view.updateSeatState(expectedSelectSeats2, SelectState.SUCCESS)
            view.updateSeatState(expectedSelectSeats3, SelectState.EXCEED)
        }
    }

    @Test
    @DisplayName("선택된 좌석들의 예매 가격을 계산하여 뷰에게 전달한다.")
    fun update_view_When_complete_selected_seats_price() {
        // when
        every { view.showSeat(any()) } just Runs
        every { view.updateSeatState(any(), any()) } just Runs
        every { view.updatePrice(any()) } just Runs
        presenter.loadInitData(0, 3)

        // given
        presenter.changeSeatState(selectedSeat1)

        // then
        verify(exactly = 1) { view.updatePrice(15_000) }
    }

    companion object {
        private val selectedSeat1 = SeatUiModel(2, 1, SeatRate.S)
        private val selectedSeat2 = SeatUiModel(2, 2, SeatRate.S)
        private val selectedSeat3 = SeatUiModel(2, 3, SeatRate.S)
    }
}
