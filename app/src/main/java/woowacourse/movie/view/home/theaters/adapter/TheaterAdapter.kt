package woowacourse.movie.view.home.theaters.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.home.theaters.TheaterListEventHandler
import woowacourse.movie.view.home.theaters.viewholder.TheaterViewHolder

class TheaterAdapter(
    private val movieId: Int,
    private val items: Theaters,
    private val handler: TheaterListEventHandler,
) : ListAdapter<Theater, RecyclerView.ViewHolder>(
        object : DiffUtil.ItemCallback<Theater>() {
            override fun areItemsTheSame(
                oldItem: Theater,
                newItem: Theater,
            ): Boolean {
                return when {
                    oldItem.name == newItem.name -> true
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: Theater,
                newItem: Theater,
            ): Boolean {
                return oldItem == newItem
            }
        },
    ) {
    override fun getItemCount(): Int = items.size()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        return TheaterViewHolder(parent, handler)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = items[position]
        (holder as TheaterViewHolder).bind(item, item.screeningTimeCount(movieId))
    }
}
