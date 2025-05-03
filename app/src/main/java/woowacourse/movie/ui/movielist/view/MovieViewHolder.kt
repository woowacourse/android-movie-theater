package woowacourse.movie.ui.movielist.view

import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.model.movie.Movie
import woowacourse.movie.utils.StringFormatter

class MovieViewHolder(
    val itemBinding: MovieItemBinding,
    val onClickBooking: (Movie) -> Unit,
) : RecyclerView.ViewHolder(itemBinding.root) {
    private var currentMovieItem: Movie? = null
    private val bookingBtn: Button = itemView.findViewById<Button>(R.id.btn_booking)

    init {
        bookingBtn.setOnClickListener {
            currentMovieItem?.let {
                onClickBooking(it)
            }
        }
    }

    fun bind(item: Movie) {
        currentMovieItem = item
        itemBinding.stringFormatter = StringFormatter
        itemBinding.movie = item
    }
}
