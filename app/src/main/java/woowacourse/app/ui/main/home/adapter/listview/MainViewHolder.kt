package woowacourse.app.ui.main.home.adapter.listview

import android.view.View
import woowacourse.app.model.HomeData
import woowacourse.app.ui.main.home.adapter.HomeViewType

sealed class MainViewHolder(val view: View) {
    abstract fun onBind(data: HomeData)
    abstract val homeViewType: HomeViewType
}
