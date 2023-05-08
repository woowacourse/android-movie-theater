package woowacourse.movie.presentation.view.main.home.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.Theater

class TheaterViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_theater_list, parent, false)
) {
    fun bind(theater: Theater, movieSchedule: List<String>) {
        itemView.findViewById<TextView>(R.id.tv_theater_name).text = theater.name
        itemView.findViewById<TextView>(R.id.tv_theater_screening_info).text =
            "${(movieSchedule.size)}개의 상영시간"
    }
}