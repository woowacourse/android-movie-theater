package woowacourse.movie.feature.movieList

import com.example.domain.usecase.GetMovieAndAdvItemsUseCase
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.movieList.itemModel.AdvItemModel
import woowacourse.movie.feature.movieList.itemModel.MovieItemModel
import woowacourse.movie.model.SelectTheaterAndMovieState
import woowacourse.movie.model.mapper.asPresentation

class MoviesPresenter(
    val view: MovieListContract.View,
    private val getMovieAndAdvItemsUseCase: GetMovieAndAdvItemsUseCase,
) : MovieListContract.Presenter {

    override fun loadMovieAndAdvItems() {
        getMovieAndAdvItemsUseCase(
            onSuccess = { movies, advs ->
                val movieItems = movies.map {
                    it.asPresentation().toItemModel { movieState ->
                        view.showTheaterBottomSheet(movieState)
                    }
                }
                val advItems = advs.map {
                    it.asPresentation().toItemModel { advState ->
                        view.navigateAdbDetail(advState)
                    }
                }
                view.updateItems(combineMovieAndAdvItems(movieItems, advItems))
            },
            onFailure = { view.errorLoadData() }
        )
    }

    override fun receiveTheaterInfo(theaterMovie: SelectTheaterAndMovieState) {
        view.navigateMovieDetail(theaterMovie)
    }

    private fun combineMovieAndAdvItems(
        movies: List<MovieItemModel>,
        advs: List<AdvItemModel>
    ): List<CommonItemModel> {
        return if (advs.isEmpty()) {
            movies.toList()
        } else {
            var curAdvIndex = 0
            val advSize = advs.size
            val allowAdvMaxCount: Int = movies.size / 3
            mutableListOf<CommonItemModel>().apply {
                addAll(movies.toList())
                for (index in 3..(movies.size + allowAdvMaxCount) step 4) {
                    add(index, advs[(curAdvIndex++) % advSize])
                }
            }
        }
    }
}
