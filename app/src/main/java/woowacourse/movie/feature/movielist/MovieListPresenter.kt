package woowacourse.movie.feature.movielist

import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.feature.common.itemModel.ItemModel

class MovieListPresenter(
    val view: MovieListContract.View
) : MovieListContract.Presenter {

    /*
        itemModel, movieState, advState 은 UI를 위한 모델이기 때문에 presenter가 알고 있으면 안되는 정보일까요?
        data를 위한 모델을 따로 만든 후 presenter에서는 dataModel을 사용하고,
        view에서 data model을 ui model로 변경해 사용하는 것이 올바른 방법일까요?
     */
    override fun setListItems() {
        val itemModels: MutableList<ItemModel> = getMovies().toMutableList()
        val advItemModels: List<ItemModel> = getAdvs()

        var itemPosition = 0
        var advPosition = 0
        while (itemPosition < itemModels.size) {
            if ((itemPosition + 1) % 4 == 0) {
                itemModels.add(itemPosition, advItemModels[advPosition])
                advPosition++
            }
            if (advPosition == advItemModels.size) advPosition = 0
            itemPosition++
        }

        view.setItems(itemModels)
    }

    private fun getMovies(): List<ItemModel> {
        return MovieRepository.allMovies().map { movieState ->
            movieState.toItemModel { view.navigateMovieDetail(movieState) }
        }.toList()
    }

    private fun getAdvs(): List<ItemModel> {
        return AdvRepository.allAdv().map { advState ->
            advState.toItemModel { view.navigateAdvDetail(advState) }
        }
    }
}
