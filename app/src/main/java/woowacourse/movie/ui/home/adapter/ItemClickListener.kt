package woowacourse.movie.ui.home.adapter

import woowacourse.movie.model.MovieListModel.AdModel
import woowacourse.movie.model.MovieListModel.MovieModel

interface ItemClickListener {
    fun onMovieItemClick(movie: MovieModel)
    fun onAdItemClick(ad: AdModel)
}
