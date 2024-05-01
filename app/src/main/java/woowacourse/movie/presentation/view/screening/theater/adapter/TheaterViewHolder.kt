package woowacourse.movie.presentation.view.screening.theater.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.presentation.uimodel.TheaterUiModel

class TheaterViewHolder(
    itemView: View,
    private val onClickedListener: (Int) -> Unit, // id
) : RecyclerView.ViewHolder(itemView) {
     val name: TextView = itemView.findViewById(R.id.theater_name)
     val count: TextView = itemView.findViewById(R.id.theater_movie_screen_time_count)

    init {
        itemView.setOnClickListener {
            onClickedListener(adapterPosition)
        }
    }

    fun bind(theater: TheaterUiModel) {
        name.text = theater.name
        count.text = theater.screeningCount.toString()
    }
}
