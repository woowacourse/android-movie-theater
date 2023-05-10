package woowacourse.movie.ui.main.adapter.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.model.main.MainData
import woowacourse.movie.ui.main.adapter.MainViewType

sealed class MainViewHolder<out T : ViewDataBinding>(val binding: T) :
    RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(data: MainData)
    abstract val mainViewType: MainViewType
}
