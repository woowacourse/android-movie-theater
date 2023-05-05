package woowacourse.movie.presentation.choiceSeat

import woowacourse.movie.domain.model.tools.Movie

interface ChoiceSeatContract {
    interface View {
        val presenter: Presenter
    }

    interface Presenter {
        val view: View

        fun getMovieById(movieId: Long): Movie

        fun getNotificationSettings(): Boolean
    }
}
