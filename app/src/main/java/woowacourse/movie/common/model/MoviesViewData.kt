package woowacourse.movie.common.model

import java.io.Serializable

class MoviesViewData(val value: List<MovieViewData>) : Serializable {
    companion object {
        const val MOVIES_EXTRA_NAME = "movies"
    }
}
