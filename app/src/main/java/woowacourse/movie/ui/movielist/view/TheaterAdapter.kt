package woowacourse.movie.ui.movielist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.ui.movielist.view.listener.TheaterClickListener

class TheaterAdapter(
    private val movieId: Long,
    private val clickListener: TheaterClickListener,
) : ListAdapter<Theater, TheaterViewHolder>(
    object : DiffUtil.ItemCallback<Theater>() {
        override fun areItemsTheSame(
            oldItem: Theater,
            newItem: Theater,
        ): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(
            oldItem: Theater,
            newItem: Theater,
        ): Boolean {
            return oldItem == newItem
        }
    },
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = TheaterItemBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(movieId, itemBinding, clickListener)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        holder.bind(item)
    }
}
