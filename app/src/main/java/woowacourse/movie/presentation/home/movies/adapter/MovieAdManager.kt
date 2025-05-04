package woowacourse.movie.presentation.home.movies.adapter

import woowacourse.movie.R
import woowacourse.movie.presentation.common.model.MovieUiModel
import woowacourse.movie.presentation.home.movies.adapter.item.AdItem
import woowacourse.movie.presentation.home.movies.adapter.item.MovieItem
import woowacourse.movie.presentation.home.movies.adapter.item.MovieMainItem

class MovieAdManager {
    fun insertAds(movies: List<MovieUiModel>): List<MovieMainItem> {
        val items = mutableListOf<MovieMainItem>()
        movies.forEachIndexed { index, movie ->
            items.add(MovieItem(movie))
            if ((index + MINIMUM_INDEX) % AD_FREQUENCY == 0) {
                items.add(AdItem(R.drawable.woowacourse_ad))
            }
        }
        return items
    }

    companion object {
        private const val MINIMUM_INDEX = 1
        private const val AD_FREQUENCY = 3
    }
}
