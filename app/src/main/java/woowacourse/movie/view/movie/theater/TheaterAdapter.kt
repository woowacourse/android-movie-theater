package woowacourse.movie.view.movie.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.Movie
import woowacourse.movie.model.Theater
import woowacourse.movie.model.TheaterUIModel
import woowacourse.movie.view.movie.TheaterClickListener

class TheaterAdapter(
    private val clickListener: TheaterClickListener,
    val movie: Movie,
) : ListAdapter<Theater, RecyclerView.ViewHolder>(TheaterDiffUtil) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_theater, parent, false)
        return TheaterViewHolder(view, clickListener)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val theater = (getItem(position))
        (holder as TheaterViewHolder).bind(TheaterUIModel(theater.name, movie, theater.getTotalTimeSlotCount(movie)))
    }
}
