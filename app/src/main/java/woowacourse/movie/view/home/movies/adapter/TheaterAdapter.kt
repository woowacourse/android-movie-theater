package woowacourse.movie.view.home.movies.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.view.base.BaseViewHolder
import woowacourse.movie.view.home.movies.model.TheaterRvItem
import woowacourse.movie.view.home.movies.viewholder.TheaterViewHolder

class TheaterAdapter(
    private val items: List<TheaterRvItem>,
    private val handler: Handler,
) : RecyclerView.Adapter<BaseViewHolder<TheaterRvItem>>() {
    override fun getItemCount(): Int = items.size

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseViewHolder<TheaterRvItem> {
        return when (val type = TheaterRvItem.ViewType.entries[viewType]) {
            TheaterRvItem.ViewType.VIEW_TYPE_THEATER ->
                TheaterViewHolder(
                    parent,
                    type.layoutRes,
                    handler,
                )
        } as BaseViewHolder<TheaterRvItem>
    }

    override fun onBindViewHolder(
        holder: BaseViewHolder<TheaterRvItem>,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    interface Handler : TheaterViewHolder.Handler
}
