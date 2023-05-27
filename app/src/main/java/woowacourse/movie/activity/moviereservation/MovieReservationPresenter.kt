package woowacourse.movie.activity.moviereservation

import woowacourse.movie.domain.model.Count
import woowacourse.movie.domain.model.ReservationDetail
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.LocalFormattedTime
import woowacourse.movie.view.data.TheaterMovieViewData
import woowacourse.movie.view.mapper.ReservationDetailMapper.toView
import woowacourse.movie.view.widget.Counter
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class MovieReservationPresenter(
    val view: MovieReservationContract.View,
    private val theaterMovie: TheaterMovieViewData,
    private val count: Int,
    private val savedDateIndex: Int,
    private val savedTimeIndex: Int,
) : MovieReservationContract.Presenter {

    private val counter = Counter(Count(count))

    override fun setUpMovie(theaterMovie: TheaterMovieViewData) {
        view.setUpMovie(theaterMovie)
    }

    override fun loadCountData() {
        view.initCount(count)
    }

    override fun loadDateTimeData() {
        view.initDateSpinner(
            savedDateIndex,
            theaterMovie.movie.date.toList().map { LocalFormattedDate(it) },
        )
        view.initTimeSpinner(
            savedTimeIndex,
            theaterMovie.screenTimes.map { LocalFormattedTime(it) },
        )
    }

    override fun plusCount() {
        view.updateCount(counter.add())
    }

    override fun minusCount() {
        view.updateCount(counter.minus())
    }

    override fun navigateToSeatSelection(
        date: LocalDate,
        time: LocalTime,
        theaterName: String,
    ) {
        view.navigateToSeatSelection(
            theaterMovie,
            ReservationDetail(
                LocalDateTime.of(date, time),
                counter.count.value,
                theaterName,
            ).toView(),
        )
    }

    override fun getCount(): Int {
        return counter.count.value
    }
}
