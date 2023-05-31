package woowacourse.movie.feature.movielist

import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.feature.common.itemModel.AdvItemModel
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.common.itemModel.MovieItemModel
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.mapper.asPresentation

class MovieListPresenter(
    val view: MovieListContract.View,
    val movieRepo: MovieRepository,
    val advRepo: AdvRepository
) : MovieListContract.Presenter {

    override fun loadMovieAndAdvItems() {
        val movies: List<MovieState> = movieRepo.allMovies().map { it.asPresentation() }
        val advs: List<AdvState> = advRepo.allAdv()

        val movieItems = movies.map {
            it.toItemModel { movieState ->
                view.showTheaterBottomSheet(movieState)
            }
        }
        val advItems = advs.map {
            it.toItemModel { advState ->
                view.navigateAdbDetail(advState)
            }
        }
        view.updateItems(combineMovieAndAdvItems(movieItems, advItems))
    }

    override fun receiveTheaterInfo(theaterMovie: SelectTheaterAndMovieState) {
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
