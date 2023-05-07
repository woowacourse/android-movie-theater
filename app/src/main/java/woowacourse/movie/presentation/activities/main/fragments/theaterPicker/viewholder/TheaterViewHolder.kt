package woowacourse.movie.presentation.activities.main.fragments.theaterPicker.viewholder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation
import woowacourse.movie.presentation.model.item.Theater

class TheaterViewHolder(
    view: View,
    onClick: (Int) -> Unit,
) : BaseRecyclerView.BaseViewHolder(view) {
    private val theaterName: TextView = view.findViewById(R.id.theater_name)
    private val theaterMovieTime: TextView = view.findViewById(R.id.theater_movie_time)
    private val next: ImageView = view.findViewById(R.id.next)

    init {
        next.setOnClickListener { onClick(adapterPosition) }
    }

    override fun <T>bind(item: T) {
        when (item) {
            is Theater -> {
                theaterName.text = item.theaterName
                theaterMovieTime.text = theaterMovieTime.context.getString(R.string.theater_movie_time, item.screenTimes.size)
            }
            is Reservation -> {}
            is Movie -> {}
            is Ad -> {}
        }
    }
}
