package woowacourse.movie.presentation.view.main.home.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.presentation.model.Theater

class TheaterViewHolder(private val parent: ViewGroup, private val event: (Int) -> Unit) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_theater_list, parent, false)
    ) {

    init {
        itemView.findViewById<ConstraintLayout>(R.id.cl_parent).setOnClickListener {
            event(adapterPosition)
        }
    }

    fun bind(theater: Theater, movieSchedule: List<String>) {
        itemView.findViewById<TextView>(R.id.tv_theater_name).text = theater.name
        itemView.findViewById<TextView>(R.id.tv_theater_screening_info).text =
            parent.context.getString(R.string.theater_count_movie_time_schedule, movieSchedule.size)
    }
}