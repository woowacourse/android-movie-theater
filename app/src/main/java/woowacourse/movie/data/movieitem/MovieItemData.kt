package woowacourse.movie.data.movieitem

import woowacourse.movie.presentation.movielist.movie.MovieItem

interface MovieItemData {

    fun getMovieItems(): List<MovieItem>
}
