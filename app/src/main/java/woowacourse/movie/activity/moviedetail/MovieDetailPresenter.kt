package woowacourse.movie.activity.moviedetail

import com.woowacourse.domain.Counter
import woowacourse.movie.mapper.toPresentation
import woowacourse.movie.movie.Movie

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
    private var counter: Counter = Counter(MIN_COUNT),
    val movieData: Movie,
) :
    MovieDetailContract.Presenter {

    override fun isMinTicketCount(): Boolean = counter.count == MIN_COUNT

    override fun getTicketCount(): Int {
        return counter.toPresentation().count
    }

    override fun addTicket() {
        counter = counter.add()
        view.setTicketCountText(counter.toPresentation().count)
    }

    override fun subTicket() {
        counter = counter.sub()
        view.setTicketCountText(counter.toPresentation().count)
    }

    override fun onClickMoveSeatPickerPageButton() {
        view.startSeatPickerPage(movieData)
    }

    companion object {
        private const val MIN_COUNT = 1
    }
}
