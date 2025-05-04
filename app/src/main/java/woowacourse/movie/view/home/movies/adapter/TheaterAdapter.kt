package woowacourse.movie.view.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.domain.model.theater.Theaters
import woowacourse.movie.view.home.movies.viewholder.TheaterViewHolder

class TheaterAdapter(
    private val items: Theaters,
    private val movieId: Int,
    private val onclick: (Theater) -> Unit,
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
        val inflater =
            LayoutInflater.from(parent.context)

        val theaterItemBinding = TheaterItemBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(theaterItemBinding, onclick)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = items[position]
        (holder as TheaterViewHolder).bind(item, item.screeningTimeCount(movieId))
    }
}
