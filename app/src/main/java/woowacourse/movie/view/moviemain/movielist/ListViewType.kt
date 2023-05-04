package woowacourse.movie.view.moviemain.movielist

import androidx.annotation.LayoutRes
import woowacourse.movie.R

enum class ListViewType(@LayoutRes val id: Int) {
    AD_VIEWTYPE(R.layout.movie_ad_item), NORMAL_VIEWTYPE(R.layout.movie_item);

    companion object {
        fun getViewType(adInterval: Int, position: Int): ListViewType {
            if ((position + 1) % (adInterval + 1) == 0) return AD_VIEWTYPE
            return NORMAL_VIEWTYPE
        }
    }
}
