package woowacourse.movie.view.home.movies.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.domain.Showing
import woowacourse.movie.view.home.theater.OnTheaterEventListener

class TheaterAdapter(
    private val eventListener: OnTheaterEventListener,
) : ListAdapter<Showing, TheaterViewHolder>(TheaterItemDiffCallback) {
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
            object : DiffUtil.ItemCallback<Showing>() {
                override fun areItemsTheSame(
                    oldItem: Showing,
                    newItem: Showing,
                ): Boolean {
                    return oldItem == newItem
                }

                override fun areContentsTheSame(
                    oldItem: Showing,
                    newItem: Showing,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
