package woowacourse.movie.bottomsheat

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.Theater
import woowacourse.movie.movie.Movie

class TheaterRecyclerViewHolder(
    private val movie: Movie,
    private val view: View,
    private val listener: (Int) -> Unit,
) : RecyclerView.ViewHolder(view) {

    val theaterName: TextView = view.findViewById(R.id.tv_theater_name)
    val runningTimeCount: TextView = view.findViewById(R.id.tv_running_time_count)

    init {
        view.setOnClickListener {
            listener(bindingAdapterPosition)
        }
    }

    fun bind(theater: Theater) {
        theaterName.text = theater.name
        runningTimeCount.text = view.context.getString(
            R.string.bottom_sheet_dialog_running_theater_count,
            theater.screenTimes[movie]?.size ?: 0
        )
    }
}
