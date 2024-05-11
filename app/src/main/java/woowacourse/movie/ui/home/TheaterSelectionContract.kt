package woowacourse.movie.ui.home

import woowacourse.movie.domain.Theater
import woowacourse.movie.ui.HandleError

interface TheaterSelectionContract {
    interface View : HandleError {
        fun showTheaters(
            movieContentId: Long,
            theaters: List<Theater>,
        )
    }

    interface Presenter {
        fun loadTheaters(movieContentId: Long)
    }
}
