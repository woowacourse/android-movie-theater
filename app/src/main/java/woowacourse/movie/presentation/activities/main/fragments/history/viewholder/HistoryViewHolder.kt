package woowacourse.movie.presentation.activities.main.fragments.history.viewholder

import android.view.View
import android.widget.TextView
import woowacourse.movie.R
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie
import woowacourse.movie.presentation.model.item.Reservation
import woowacourse.movie.presentation.model.item.Theater

class HistoryViewHolder(
    view: View,
    onClick: (Int) -> Unit,
) : BaseRecyclerView.BaseViewHolder(view) {
    private val movieDateTextView: TextView = view.findViewById(R.id.movie_date_text_view)
    private val movieTimeTextView: TextView = view.findViewById(R.id.movie_time_text_view)
    private val movieTitleTextView: TextView = view.findViewById(R.id.movie_title_text_view)

    init {
        view.setOnClickListener { onClick(adapterPosition) }
        movieTitleTextView.setOnClickListener { onClick(adapterPosition) }
    }

    override fun <T>bind(item: T) {
        when (item) {
            is Reservation -> {
                movieDateTextView.text = item.formattedDate
                movieTimeTextView.text = item.formattedTime
                movieTitleTextView.text = item.movieTitle
            }
            is Movie -> {}
            is Ad -> {}
            is Theater -> {}
        }
    }
}
