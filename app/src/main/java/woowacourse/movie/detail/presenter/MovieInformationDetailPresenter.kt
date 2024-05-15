package woowacourse.movie.detail.presenter

import woowacourse.movie.common.CommonDataSource
import woowacourse.movie.detail.contract.MovieInformationDetailContract
import woowacourse.movie.detail.model.DetailDataResource
import woowacourse.movie.detail.model.DetailDataResource.movieId
import woowacourse.movie.detail.model.DetailDataResource.theaterId
import woowacourse.movie.detail.model.DetailTicketCountData
import woowacourse.movie.list.model.TheaterData
import java.time.LocalTime

class MovieInformationDetailPresenter(
    private val view: MovieInformationDetailContract.View,
) : MovieInformationDetailContract.Presenter {
    override val model = DetailTicketCountData

    private val theater
        get() = TheaterData.theaters.first { it.id == theaterId }

    private val screeningTimes: List<LocalTime>
        get() = theater.getScreeningTimes(DetailDataResource.movieId)

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
        val movieId = DetailDataResource.movieId
        view.setMovieView(CommonDataSource.movieList.first { it.id == movieId })
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
        view.startMovieTicketActivity(ticketCount, theaterId, movieId)
    }

    override fun setSpinnerInfo() {
        view.showSpinner(DetailDataResource.screeningDates, screeningTimes)
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
