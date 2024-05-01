package woowacourse.movie.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Theater

class TheaterViewHolder(
    itemView: View,
    private val onClickedListener: (Int) -> Unit, // id
) : RecyclerView.ViewHolder(itemView) {
    private val name: TextView = itemView.findViewById(R.id.theater_name)
    private val count: TextView = itemView.findViewById(R.id.theater_movie_screen_time_count)

    init {
        itemView.setOnClickListener {
            onClickedListener(adapterPosition)
        }
    }

    fun bind(theater: Pair<Theater, Int>) {
        name.text = theater.first.name
        count.text = theater.second.toString()
    }
}
