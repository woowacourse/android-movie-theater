package woowacourse.app.ui.main.home.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.model.HomeData
import woowacourse.app.ui.main.home.adapter.HomeViewType

sealed class HomeViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: HomeData)
    abstract val homeViewType: HomeViewType
}
