package woowacourse.movie.ui.movielist.view

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.theater.Theater

class TheaterAdapter(
    private val onClickTheater: TheaterClickListener,
) : ListAdapter<Theater, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<Theater>() {
            override fun areItemsTheSame(
                oldItem: Theater,
                newItem: Theater,
            ): Boolean = oldItem.name == newItem.name

            override fun areContentsTheSame(
                oldItem: Theater,
                newItem: Theater,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder = TheaterViewHolder.from(parent, onClickTheater)

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        (holder as TheaterViewHolder).bind(item)
    }
}
