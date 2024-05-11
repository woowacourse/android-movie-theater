package woowacourse.movie.selectseat

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Seats
import woowacourse.movie.repository.EverythingRepository
import woowacourse.movie.selectseat.uimodel.Position
import woowacourse.movie.selectseat.uimodel.PriceUiModel
import woowacourse.movie.selectseat.uimodel.SeatState
import woowacourse.movie.selectseat.uimodel.SeatUiModel
import woowacourse.movie.selectseat.uimodel.toSeatUiModelMap
import woowacourse.movie.selectseat.uimodel.toSeats
import woowacourse.movie.usecase.PutReservationUseCase
import java.util.concurrent.FutureTask

class SelectSeatPresenter(
    private val view: SelectSeatContract.View,
    private val repository: EverythingRepository,
    private val putReservationUseCase: PutReservationUseCase,
) : SelectSeatContract.Presenter {
    private lateinit var _seats: MutableMap<Position, SeatUiModel>
    private val seats: MutableMap<Position, SeatUiModel>
        get() = _seats
    private val selectedSeats
        get() =
            seats.filter { (_, seatsUiModel) ->
                seatsUiModel.state == SeatState.SELECTED
            }.map { it.value }
    private val price: PriceUiModel
        get() {
            val totalPrice = Seats(selectedSeats.toSeats()).totalPrice
            return PriceUiModel(totalPrice.price)
        }
    private var screeningId: Long = -1
    private var maxCount = -1

    override fun initSeats(screeningId: Long) {
        runCatching {
            repository.screeningById(screeningId) ?: error(SCREENING_NOT_EXISTS_ERROR)
        }.onSuccess { screening ->
            _seats = screening.theater.seats.toSeatUiModelMap().toMutableMap()
            this.screeningId = screeningId
            view.initSeats(seats)
        }
        view.deActivatePurchase()
    }

    override fun initMaxCount(headCount: HeadCount) {
        maxCount = headCount.count
    }

    override fun selectSeat(position: Position) {
        val seat = seats[position] ?: error(NO_SEAT)
        seats[position] = seat.changeState()
        if (selectedSeats.size - 1 >= maxCount) {
            seats[position] = seat
        }
        loadSeats(seats)
    }

    override fun loadReservationInfo(movieId: Long) {
        runCatching {
            repository.screeningById(movieId) ?: error(SCREENING_NOT_EXISTS_ERROR)
        }.onSuccess {
            view.showMovieInfo(it.movie.title, PriceUiModel(Seats().totalPrice.price))
        }
    }

    override fun saveSeats() {
        view.onSaveSeats(seats)
    }

    override fun loadSeats(seats: Map<Position, SeatUiModel>) {
        this._seats = seats.toMutableMap()
        view.showSeats(this.seats)
        view.showPrice(this.price)
        view.deActivatePurchase()
        if (selectedSeats.size == maxCount) view.activatePurchase()
    }

    override fun completeReservation() {
        runCatching {
            val task =
                FutureTask<Long> {
                    putReservationUseCase(screeningId, Seats(selectedSeats.toSeats()))
                }
            Thread(task).start()
            task.get()
        }.onSuccess {
            view.navigateToResult(it)
        }
    }

    companion object {
        private const val SCREENING_NOT_EXISTS_ERROR = "상영 정보를 찾을 수 없었습니다"
        private const val NO_SEAT = "해당하는 좌표에 좌석이 없습니다"
    }
}
