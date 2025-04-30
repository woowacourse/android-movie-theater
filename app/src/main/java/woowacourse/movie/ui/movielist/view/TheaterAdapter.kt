package woowacourse.movie.ui.movielist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.Theater

class TheaterAdapter(
    private val onClickTheater: (Theater) -> Unit,
) : ListAdapter<Theater, RecyclerView.ViewHolder>(
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
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = TheaterItemBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(itemBinding, onClickTheater)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)
        (holder as TheaterViewHolder).bind(item)
    }
}
