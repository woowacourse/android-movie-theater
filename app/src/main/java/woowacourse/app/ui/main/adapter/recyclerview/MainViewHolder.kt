package woowacourse.app.ui.main.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.model.MainData
import woowacourse.app.ui.main.adapter.MainViewType

sealed class MainViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: MainData)
    abstract val mainViewType: MainViewType
}
