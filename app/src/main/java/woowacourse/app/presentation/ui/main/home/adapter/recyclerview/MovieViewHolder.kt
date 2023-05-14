package woowacourse.app.presentation.ui.main.home.adapter.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.presentation.model.HomeData
import woowacourse.app.presentation.ui.main.home.adapter.HomeViewType

sealed class MovieViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    abstract fun onBind(data: HomeData)
    abstract val homeViewType: HomeViewType
}
