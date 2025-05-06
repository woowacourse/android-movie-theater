package woowacourse.movie.view.home.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
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
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val theaterBinding =
            DataBindingUtil.inflate<ItemTheaterBinding>(
                inflater,
                R.layout.item_theater,
                parent,
                false,
            )
        return TheaterViewHolder(theaterBinding, onTheaterClick)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }
}
