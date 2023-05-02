package woowacourse.app.ui.main.adapter.listview

import android.view.View
import woowacourse.app.model.main.MainData
import woowacourse.app.ui.main.adapter.MainViewType

sealed class MainViewHolder(val view: View) {
    abstract fun onBind(data: MainData)
    abstract val mainViewType: MainViewType
}
