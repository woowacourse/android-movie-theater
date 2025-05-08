package woowacourse.movie.domain

import androidx.annotation.DrawableRes

sealed class MovieListItem {
    abstract val viewType: Int

    data class ItemMovie(val movie: Movie) : MovieListItem() {
        override val viewType = ItemType.MOVIE.viewType
    }

    data class ItemAd(val ad: AdType) : MovieListItem() {
        override val viewType = ad.viewType
    }
}

enum class ItemType(val viewType: Int) {
    MOVIE(0),
    AD_BANNER(1),
}

sealed class AdType(val viewType: Int) {
    data class Banner(
        @DrawableRes val imageUrl: Int,
    ) : AdType(ItemType.AD_BANNER.viewType)
}
