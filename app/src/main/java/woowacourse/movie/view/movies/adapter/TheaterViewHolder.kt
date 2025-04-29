package woowacourse.movie.view.movies.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.Theater
import woowacourse.movie.view.movies.OnTheaterEventListener

class TheaterViewHolder(
    view: View,
    eventListener: OnTheaterEventListener,
) : RecyclerView.ViewHolder(view) {
    private val theaterNameTextView: TextView = view.findViewById(R.id.tv_theater_name)
    private val theaterTimeTextView: TextView = view.findViewById(R.id.tv_theater_time)
    private val selectButton: ImageView = view.findViewById(R.id.btn_arrow)
    private var theater: Theater? = null

    init {
        selectButton.setOnClickListener {
            theater?.let { eventListener.onClickReservation(it) }
        }
    }

    fun bind(theater: Theater) {
        this.theater = theater
        theaterNameTextView.text = theater.name
        theaterTimeTextView.text = theater.schedule.scheduleTime.time.size.toString()
    }
}
