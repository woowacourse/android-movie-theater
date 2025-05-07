package woowacourse.movie.view.home.theater

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.model.theater.TheaterMovieSchedule

class TheaterAdapter(
    private val onTheaterClick: (TheaterMovieSchedule) -> Unit,
) : ListAdapter<TheaterMovieSchedule, TheaterViewHolder>(
        object : DiffUtil.ItemCallback<TheaterMovieSchedule>() {
            override fun areItemsTheSame(
                oldItem: TheaterMovieSchedule,
                newItem: TheaterMovieSchedule,
            ): Boolean = oldItem.theater == newItem.theater

            override fun areContentsTheSame(
                oldItem: TheaterMovieSchedule,
                newItem: TheaterMovieSchedule,
            ): Boolean = oldItem == newItem
        },
    ) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder = TheaterViewHolder.from(parent, onTheaterClick)

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
