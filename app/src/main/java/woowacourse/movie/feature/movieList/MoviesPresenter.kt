package woowacourse.movie.feature.movieList

import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel
import woowacourse.movie.feature.movieList.itemModel.MovieItemModel
import woowacourse.movie.model.TheaterMovieState

class MoviesPresenter(
    val view: MovieListContract.View,
    val movieRepository: MovieRepository,
    val advRepository: AdvRepository
) : MovieListContract.Presenter {

    override fun loadMovieAndAdvItemList() {
        val movie = movieRepository.allMovies().map {
            it.toItemModel { movie -> view.showBottomSheetDialog(movie) }
        }
        val adv = advRepository.allAdv().map {
            it.toItemModel { adv -> view.navigateAdbDetail(adv) }
        }
        view.updateItems(combineMovieAndAdvItems(movie, adv))
    }

    override fun receiveTheaterInfo(theaterMovie: TheaterMovieState) {
        view.navigateMovieDetail(theaterMovie)
    }

    private fun combineMovieAndAdvItems(
        movies: List<MovieItemModel>,
        advs: List<AdvItemModel>
    ): List<ItemModel> {
        return if (advs.isEmpty()) {
            movies.toList()
        } else {
            var curAdvIndex = 0
            val advSize = advs.size
            val allowAdvMaxCount: Int = movies.size / 3
            mutableListOf<ItemModel>().apply {
                addAll(movies.toList())
                for (index in 3..(movies.size + allowAdvMaxCount) step 4) {
                    add(index, advs[(curAdvIndex++) % advSize])
                }
            }
        }
    }
}
