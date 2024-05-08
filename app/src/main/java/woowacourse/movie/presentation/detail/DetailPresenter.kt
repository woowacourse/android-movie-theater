package woowacourse.movie.presentation.detail

import woowacourse.movie.data.SampleMovieData
import woowacourse.movie.domain.model.detail.DetailDataResource
import woowacourse.movie.domain.model.detail.DetailDataResource.movieId
import woowacourse.movie.domain.model.detail.DetailDataResource.theaterId
import woowacourse.movie.domain.model.detail.DetailTicketCountData
import woowacourse.movie.domain.model.home.TheaterData
import java.time.LocalTime

class DetailPresenter(
    private val view: DetailContract.View,
) : DetailContract.Presenter {
    val model = DetailTicketCountData

    private val theater = TheaterData.theaters.first { it.id == theaterId }

    private val screeningTimes: List<LocalTime> = theater.getScreeningTimes(movieId)

    private val ticketCount = model.ticketCount

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
        view.setMovieView(SampleMovieData.movieList.first { it.id == movieId })
    }

    override fun setPlusButtonClickInfo() {
        runCatching {
            model.plusTicketCount()
            view.showCurrentResultTicketCountView(model.ticketCount.number)
        }.onFailure {
            view.showToast(it.message ?: "")
        }
    }

    override fun setMinusButtonClickInfo() {
        runCatching {
            model.minusTicketCount()
            view.showCurrentResultTicketCountView(model.ticketCount.number)
        }.onFailure {
            view.showToast(it.message ?: "")
        }
    }

    override fun setTicketingButtonClickInfo() {
        view.startMovieTicketActivity(model.ticketCount.number, movieId, theaterId)
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
