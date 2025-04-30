package woowacourse.movie.view.cinema.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.cinema.Cinema
import woowacourse.movie.domain.reservation.Screening

class CinemaViewHolder(
    private val view: View,
    private val onClick: () -> Unit,
) : RecyclerView.ViewHolder(view) {
    private val nameView = view.findViewById<TextView>(R.id.text_view_item_cinema_name)
    private val showtimeCountView =
        view.findViewById<TextView>(R.id.text_view_item_cinema_showtime_count)

    fun bind(
        cinema: Cinema,
        screening: Screening,
    ) {
        view.setOnClickListener { onClick() }
        nameView.text = cinema.name
        val showtimesCount = cinema.showtimeCount(screening)
        showtimeCountView.text =
            view.context.getString(R.string.item_cinema_showtime_count, showtimesCount)
    }
}
