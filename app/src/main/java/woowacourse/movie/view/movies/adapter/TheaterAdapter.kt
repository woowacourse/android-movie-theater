package woowacourse.movie.view.movies.adapter

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.view.core.base.BaseViewHolder
import woowacourse.movie.view.movies.adapter.model.TheaterRvItem

class TheaterAdapter(
    private val items: List<TheaterRvItem>,
    private val handler: Handler,
) : RecyclerView.Adapter<BaseViewHolder>() {
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder {
        return when (ViewType.entries[viewType]) {
            ViewType.VIEW_TYPE_THEATER ->
                TheaterViewHolder(
                    parent,
                    R.layout.theater_item,
                    handler,
                )
        }
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder,
        position: Int,
    ) {
        when (holder) {
            is TheaterViewHolder -> holder.bind(items[position] as TheaterRvItem.TheaterItem)
        }
    }

    enum class ViewType {
        VIEW_TYPE_THEATER,
    }

    interface Handler : TheaterViewHolder.Handler
}
