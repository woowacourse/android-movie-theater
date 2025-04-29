package woowacourse.movie.feature

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Theater

class TheaterViewHolder(
    view: View,
) : RecyclerView.ViewHolder(view) {
    private val name: TextView = view.findViewById(R.id.tv_theater_name)
    private val time: TextView = view.findViewById(R.id.tv_theater_time)
    private val bookingButton: ImageView = view.findViewById(R.id.iv_theater_arrow)

    fun bind(
        theater: Theater,
        onBookingClick: (Theater) -> Unit,
    ) {
        name.text = theater.name
        time.text = theater.timesCount.toString()
        bookingButton.setOnClickListener { onBookingClick(theater) }
    }
}
