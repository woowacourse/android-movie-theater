package woowacourse.movie.detail.presenter

import woowacourse.movie.common.MovieDataSource
import woowacourse.movie.detail.contract.DetailContract
import woowacourse.movie.detail.model.DetailDataResource
import woowacourse.movie.detail.model.DetailDataResource.movieId
import woowacourse.movie.detail.model.DetailDataResource.theaterId
import woowacourse.movie.detail.model.DetailTicketCountData
import woowacourse.movie.list.model.TheaterData
import java.time.LocalTime

class DetailPresenter(
    private val view: DetailContract.View,
) : DetailContract.Presenter {
    val model = DetailTicketCountData
    
    private val theater = TheaterData.theaters.first { it.id == theaterId }

    private val screeningTimes: List<LocalTime> = theater.getScreeningTimes(movieId)

    private val ticketCount
        get() = model.ticketCount

    override fun setCurrentResultTicketCountInfo() {
        view.showCurrentResultTicketCountView(ticketCount.number)
    }

    override fun storeMovieId(movieId: Long) {
        DetailDataResource.movieId = movieId
    }

    override fun storeTheaterId(theaterId: Long) {
        DetailDataResource.theaterId = theaterId
    }

    override fun setMovieInfo() {
        view.setMovieView(MovieDataSource.movieList.first { it.id == movieId })
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
        view.startMovieTicketActivity(ticketCount, movieId, theaterId)
    }

    override fun setSpinnerInfo(theaterId: Long) {
        view.setSpinners(DetailDataResource.screeningDates, screeningTimes)
    }

    override fun setSpinnerDateItemInfo() {
        view.setOnSpinnerDateItemSelectedListener(DetailDataResource.screeningDates)
    }

    override fun setSpinnerTimeItemInfo() {
        view.setOnSpinnerTimeItemSelectedListener(screeningTimes)
    }

    override fun storeSelectedTime(selectedTime: LocalTime) {
        DetailDataResource.selectedScreeningTime = selectedTime
    }
}
