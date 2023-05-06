package woowacourse.movie.activity.utils

import woowacourse.movie.view.data.DateRangeViewData
import woowacourse.movie.view.data.MovieViewData
import java.time.format.DateTimeFormatter

interface MovieRenderContract {

    interface View {
        fun renderMovie(
            image: Int,
            title: String,
            date: String,
            runningTime: String,
            description: String
        )
    }

    interface Presenter {
        val view: View

        fun renderMovie(movie: MovieViewData) {
            val movieDate: String = formatMovieDate(movie.date)
            val movieRunningTime: String = formatMovieRunningTime(movie.runningTime)
            view.renderMovie(
                image = movie.poster.resource,
                title = movie.title,
                date = movieDate,
                runningTime = movieRunningTime,
                description = movie.description
            )
        }

        private fun formatMovieDate(movieDate: DateRangeViewData): String {
            val dateFormat = DateTimeFormatter.ofPattern(MOVIE_DATE_FORMAT)
            return MOVIE_DATE.format(
                dateFormat.format(movieDate.startDate), dateFormat.format(movieDate.endDate)
            )
        }

        private fun formatMovieRunningTime(movieRunningTime: Int): String {
            return MOVIE_RUNNING_TIME.format(movieRunningTime)
        }

        companion object {
            private const val MOVIE_DATE_FORMAT = "yyyy-MM-dd"
            private const val MOVIE_DATE = "상영일: %s ~ %s"
            private const val MOVIE_RUNNING_TIME = "러닝타임: %d분"
        }
    }
}
