package woowacourse.movie.presentation.views.main.fragments.theater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.theater.PresentationTheater
import woowacourse.movie.presentation.views.main.fragments.theater.adapter.viewholder.TheaterViewHolder

class TheaterListAdapter(onClick: (PresentationTheater) -> Unit) :
    BaseRecyclerView.Adapter<PresentationTheater>(onClick) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BaseRecyclerView.BaseViewHolder =
        TheaterViewHolder(
            ItemTheaterBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onItemViewClick
        )

    fun appendAll(items: List<PresentationTheater>) {
        this.items.addAll(items)
        notifyItemRangeInserted(this.items.size - items.size, items.size)
    }
}
