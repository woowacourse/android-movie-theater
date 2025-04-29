package woowacourse.movie.view.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.Theater
import woowacourse.movie.view.movies.OnTheaterEventListener

class TheaterAdapter(
    private val eventListener: OnTheaterEventListener,
) : ListAdapter<Theater, TheaterViewHolder>(TheaterItemDiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.theater_item, parent, false)

        return TheaterViewHolder(view, eventListener)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val TheaterItemDiffCallback =
            object : DiffUtil.ItemCallback<Theater>() {
                override fun areItemsTheSame(
                    oldItem: Theater,
                    newItem: Theater,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: Theater,
                    newItem: Theater,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
