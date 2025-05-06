package woowacourse.movie.feature.home.contract

import woowacourse.movie.feature.model.ContentUiModel
import woowacourse.movie.feature.model.MovieUiModel
import woowacourse.movie.feature.model.ScreeningUiModel

interface HomeContract {
    interface View {
        fun showContents(contents: List<ContentUiModel>)

        fun showTheaters(screenings: List<ScreeningUiModel>)
    }

    interface Presenter {
        fun prepareContents()

        fun selectMovieForBooking(movie: MovieUiModel)
    }
}
