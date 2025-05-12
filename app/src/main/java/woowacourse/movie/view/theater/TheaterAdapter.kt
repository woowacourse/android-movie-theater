package woowacourse.movie.view.theater

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.model.theater.TheaterUIModel

class TheaterAdapter(
    private val clickListener: TheaterClickListener,
) : ListAdapter<TheaterUIModel, RecyclerView.ViewHolder>(TheaterDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder = TheaterViewHolder.from(parent, clickListener)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        (holder as TheaterViewHolder).bind(getItem(position))
    }
}
