package woowacourse.movie.view.movies.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.theater.Theater

class TheaterViewHolder(
    private val view: View,
    private val onClick: (Theater) -> Unit,
) : RecyclerView.ViewHolder(view) {
    private val title = view.findViewById<TextView>(R.id.theater_name)
    private val movieSchedule = view.findViewById<TextView>(R.id.screening_times)
    private val selectButton = view.findViewById<ImageView>(R.id.btn_select)

    fun bind(
        item: Theater,
        movieId: Int,
    ) {
        with(item) {
            title.text = name
            movieSchedule.text =
                view.context.getString(R.string.text_schedule_size)
                    .format(item.screeningTimeCount(movieId))
            selectButton.setOnClickListener {
                onClick(item)
            }
        }
    }
}
