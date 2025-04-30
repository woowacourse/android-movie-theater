package woowacourse.movie.feature

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Screening

class TheaterViewHolder(
    private val view: View,
) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.tv_theater_name)
    private val time: TextView = view.findViewById(R.id.tv_theater_time)
    private val bookingButton: ImageView = view.findViewById(R.id.iv_theater_arrow)

    fun bind(
        screening: Screening,
        onBookingClick: (Screening) -> Unit,
    ) {
        name.text = view.context.getString(R.string.theater_name, screening.theater.name)
        time.text = view.context.getString(R.string.theater_movie_time, screening.timesCount)
        bookingButton.setOnClickListener { onBookingClick(screening) }
    }
}
