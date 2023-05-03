package woowacourse.app.ui.main.home.adapter

import android.view.View
import woowacourse.app.ui.main.home.adapter.listview.AdvertisementViewHolder
import woowacourse.app.ui.main.home.adapter.listview.MainViewHolder
import woowacourse.app.ui.main.home.adapter.listview.MovieViewHolder

enum class HomeViewType {
    CONTENT {
        override fun makeViewHolder(view: View): MainViewHolder = MovieViewHolder(view)
    },
    ADVERTISEMENT {
        override fun makeViewHolder(view: View): MainViewHolder = AdvertisementViewHolder(view)
    }, ;

    abstract fun makeViewHolder(view: View): MainViewHolder

    companion object {
        fun getMainViewType(ordinal: Int): HomeViewType {
            return values()[ordinal]
        }
    }
}
