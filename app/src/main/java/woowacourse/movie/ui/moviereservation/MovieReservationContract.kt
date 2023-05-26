package woowacourse.movie.ui.moviereservation

import android.os.Bundle
import woowacourse.movie.data.model.uimodel.MovieUIModel
import java.time.LocalDateTime

interface MovieReservationContract {
    interface View {
        var presenter: Presenter

        fun setCounterText(count: String)
    }
    interface Presenter {
        fun add()

        fun sub()

        fun getCount(): Int

        fun setCount(savedInstanceState: Bundle?)

        fun save(outState: Bundle)

        fun setPicker(movie: MovieUIModel, savedInstanceState: Bundle?)

        fun getSelectedDateTime(): LocalDateTime
    }
}
