package woowacourse.movie.view.movies.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.view.core.base.BaseViewHolder
import woowacourse.movie.view.movies.adapter.model.TheaterRvItem

class TheaterViewHolder(
    parent: ViewGroup,
    @LayoutRes layoutRes: Int,
    private val handler: Handler,
) : BaseViewHolder(parent, layoutRes) {
    fun bind(item: TheaterRvItem.TheaterItem) {
        TheaterItemBinding.bind(itemView).apply {
            model = item
            eventListener = handler
        }
    }

    interface Handler {
        fun onSelectTheater(theaterName: String)
    }
}
