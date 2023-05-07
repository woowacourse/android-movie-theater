package woowacourse.movie.ui.moviereservation

import android.os.Bundle
import woowacourse.movie.data.model.uimodel.MovieUiModel
import java.time.LocalDateTime

interface MovieReservationContract {
    interface View {
        var presenter: Presenter

        fun setCounterText(count: Int)
    }
    interface Presenter {
        fun add()

        fun sub()

        fun getCount(): Int

        fun setCount(savedInstanceState: Bundle?)

        fun save(outState: Bundle)

        fun setPicker(movie: MovieUiModel, savedInstanceState: Bundle?)

        fun getSelectedDateTime(): LocalDateTime
    }
}
