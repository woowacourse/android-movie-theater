package woowacourse.movie.reservation.presenter

import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.list.model.TheaterData
import woowacourse.movie.reservation.contract.MovieReservationContract
import woowacourse.movie.reservation.model.DataResource
import woowacourse.movie.reservation.model.MovieReservationTicketCountData
import java.time.LocalTime

class MovieReservationPresenter(
    private val view: MovieReservationContract.View,
) : MovieReservationContract.Presenter {
    val model = MovieReservationTicketCountData
    lateinit var screeningTimes: List<LocalTime>

    private val ticketCount
        get() = model.ticketCount

    override fun setCurrentResultTicketCountInfo() {
        view.showCurrentResultTicketCountView(ticketCount.number)
    }

    override fun storeMovieId(movieId: Long) {
        DataResource.movieId = movieId
    }

    override fun setMovieInfo() {
        val movieId = DataResource.movieId.toInt()
        view.setMovieView(MovieDataSource.movieList[movieId])
    }

    override fun setPlusButtonClickInfo() {
        model.plusTicketCount()
        view.showCurrentResultTicketCountView(ticketCount.number)
    }

    override fun setMinusButtonClickInfo() {
        runCatching {
            model.minusTicketCount()
            view.showCurrentResultTicketCountView(ticketCount.number)
        }.onFailure {
            view.showToast(it.message ?: "")
        }
    }

    override fun setTicketingButtonClickInfo() {
        view.startMovieTicketActivity(ticketCount)
    }

    override fun setSpinnerInfo(theaterId: Long) {
        val theater = TheaterData.theaters.first { it.id == theaterId }
        screeningTimes = theater.getScreeningTimes(DataResource.movieId)
        view.showSpinner(DataResource.screeningDates, screeningTimes)
    }

    override fun setSpinnerDateItemInfo() {
        view.setOnSpinnerDateItemSelectedListener(DataResource.screeningDates)
    }

    override fun setSpinnerTimeItemInfo() {
        view.setOnSpinnerTimeItemSelectedListener(screeningTimes)
    }

    override fun storeSelectedTime(selectedTime: LocalTime) {
        DataResource.selectedScreeningTime = selectedTime
    }
}
