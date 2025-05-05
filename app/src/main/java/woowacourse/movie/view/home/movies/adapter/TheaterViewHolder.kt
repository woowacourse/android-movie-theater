package woowacourse.movie.view.home.movies.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.Showings
import woowacourse.movie.view.home.theater.OnTheaterEventListener
import java.time.LocalTime

class TheaterViewHolder(
    view: View,
    eventListener: OnTheaterEventListener,
) : RecyclerView.ViewHolder(view) {
    private val theaterNameTextView: TextView = view.findViewById(R.id.tv_theater_name)
    private val theaterTimeTextView: TextView = view.findViewById(R.id.tv_theater_time)
    private val selectButton: ImageView = view.findViewById(R.id.btn_arrow)
    private var showings: Showings? = null

    init {
        selectButton.setOnClickListener {
            showings?.let { eventListener.onClickReservation(it) }
        }
    }

    fun bind(showings: Showings) {
        this.showings = showings
        theaterNameTextView.text = showings.theaterName
        theaterTimeTextView.text = showings.showings.afterCurrentTimeSchedule(LocalTime.now()).size.toString()
    }
}
