package woowacourse.movie.data

import java.io.Serializable

class MoviesViewData(val value: List<MovieViewData>) : Serializable {
    companion object {
        const val MOVIES_EXTRA_NAME = "movies"
    }
}
