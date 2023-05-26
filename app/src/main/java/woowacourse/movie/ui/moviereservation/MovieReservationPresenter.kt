package woowacourse.movie.ui.moviereservation

import android.os.Bundle
import woowacourse.movie.data.model.Counter
import woowacourse.movie.data.model.MovieDateTimePicker
import woowacourse.movie.data.model.uimodel.MovieUIModel
import java.time.LocalDateTime

class MovieReservationPresenter(
    private val view: MovieReservationContract.View,
    private val counter: Counter,
    private val picker: MovieDateTimePicker
) : MovieReservationContract.Presenter {

    override fun add() {
        view.setCounterText(counter.add().toString())
    }

    override fun sub() {
        view.setCounterText(counter.sub().toString())
    }

    override fun getCount(): Int {
        return counter.count
    }

    override fun setCount(savedInstanceState: Bundle?) {
        counter.load(savedInstanceState)
        view.setCounterText(counter.toString())
    }

    override fun setPicker(movie: MovieUIModel, savedInstanceState: Bundle?) {
        picker.makeView(movie, savedInstanceState)
    }

    override fun save(outState: Bundle) {
        counter.save(outState)
        picker.save(outState)
    }

    override fun getSelectedDateTime(): LocalDateTime = picker.getSelectedDateTime()
}
