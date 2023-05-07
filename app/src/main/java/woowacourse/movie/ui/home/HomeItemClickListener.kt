package woowacourse.movie.ui.home

import woowacourse.movie.model.MovieListModel.AdModel
import woowacourse.movie.model.MovieListModel.MovieModel

interface HomeItemClickListener {
    fun onMovieItemClick(movie: MovieModel)
    fun onAdItemClick(ad: AdModel)
}
