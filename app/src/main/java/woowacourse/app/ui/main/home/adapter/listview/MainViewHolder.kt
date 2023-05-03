package woowacourse.app.ui.main.home.adapter.listview

import android.view.View
import woowacourse.app.model.MainData
import woowacourse.app.ui.main.home.adapter.MainViewType

sealed class MainViewHolder(val view: View) {
    abstract fun onBind(data: MainData)
    abstract val mainViewType: MainViewType
}
