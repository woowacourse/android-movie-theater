package woowacourse.app.ui.main.adapter

import android.view.View
import woowacourse.app.ui.main.adapter.listview.AdvertisementViewHolder
import woowacourse.app.ui.main.adapter.listview.MainViewHolder
import woowacourse.app.ui.main.adapter.listview.MovieViewHolder

enum class MainViewType {
    CONTENT {
        override fun makeViewHolder(view: View): MainViewHolder = MovieViewHolder(view)
    },
    ADVERTISEMENT {
        override fun makeViewHolder(view: View): MainViewHolder = AdvertisementViewHolder(view)
    }, ;

    abstract fun makeViewHolder(view: View): MainViewHolder

    companion object {
        fun getMainViewType(ordinal: Int): MainViewType {
            return values()[ordinal]
        }
    }
}
