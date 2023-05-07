package woowacourse.movie.data.movieitem

import woowacourse.movie.R
import woowacourse.movie.presentation.movielist.movie.MovieItem

object AdData {
    val ads = List(10000) { MovieItem.Ad(R.drawable.woowa_ad) }
}
