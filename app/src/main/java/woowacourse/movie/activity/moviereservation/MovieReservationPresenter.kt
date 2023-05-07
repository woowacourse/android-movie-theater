package woowacourse.movie.activity.moviereservation

import android.os.Bundle
import woowacourse.movie.domain.model.Count
import woowacourse.movie.domain.model.ReservationDetail
import woowacourse.movie.view.data.LocalFormattedDate
import woowacourse.movie.view.data.LocalFormattedTime
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.TheaterMovieViewData
import woowacourse.movie.view.mapper.ReservationDetailMapper.toView
import woowacourse.movie.view.widget.Counter
import woowacourse.movie.view.widget.SaveState
import woowacourse.movie.view.widget.SaveStateCounter
import java.time.LocalDateTime

class MovieReservationPresenter(
    override val view: MovieReservationContract.View,
    private val saveStateDateSpinner: SaveState,
    private val saveStateTimeSpinner: SaveState
) : MovieReservationContract.Presenter {

    private val counter = Counter(Count(1))

    private val saveStateCounter = SaveStateCounter(counter, COUNTER_SAVE_STATE_KEY)

    override fun saveCount(bundle: Bundle) {
        saveStateCounter.save(bundle)
    }

    override fun saveDate(bundle: Bundle) {
        saveStateDateSpinner.save(bundle)
    }

    override fun saveTime(bundle: Bundle) {
        saveStateTimeSpinner.save(bundle)
    }

    override fun initCount(bundle: Bundle?) {
        val count = saveStateCounter.load(bundle)
        view.initCount(count.value)
    }

    override fun initDateSpinner(
        movie: TheaterMovieViewData,
        bundle: Bundle?
    ) {
        val dateIndex = saveStateDateSpinner.load(bundle) as Int
        val dates = movie.movie.date.toList().map { LocalFormattedDate(it) }
        view.initDateSpinner(dateIndex, dates)
    }

    override fun initTimeSpinner(theaterMovie: TheaterMovieViewData, bundle: Bundle?) {
        val timeIndex = saveStateTimeSpinner.load(bundle) as Int
        view.initTimeSpinner(timeIndex, theaterMovie.screenTimes.map { LocalFormattedTime(it) })
    }

    override fun plusCount() {
        view.setCount(counter.add())
    }

    override fun minusCount() {
        view.setCount(counter.minus())
    }

    override fun getReservationDetailView(
        date: LocalDateTime,
        theaterName: String
    ): ReservationDetailViewData {
        return ReservationDetail(date, counter.count.value, theaterName).toView()
    }

    companion object {
        private const val COUNTER_SAVE_STATE_KEY = "counter"
    }
}
