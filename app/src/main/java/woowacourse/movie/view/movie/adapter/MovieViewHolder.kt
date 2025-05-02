package woowacourse.movie.view.movie.adapter

import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.movie.MovieClickListener
import java.time.LocalDate

@BindingAdapter("imgResPath")
fun setImageViewResourcePath(
    imageView: ImageView,
    @DrawableRes resId: Int,
) {
    imageView.setImageResource(resId)
}

@BindingAdapter(value = ["startDate", "endDate"])
fun setScreeningDate(
    view: TextView,
    startDate: LocalDate,
    endDate: LocalDate,
) {
    val text =
        view.context.getString(
            R.string.movie_screening_date,
            ReservationUiFormatter.localDateToUI(startDate),
            ReservationUiFormatter.localDateToUI(endDate),
        )
    view.text = text
}

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    clickListener: MovieClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.movieClickListener = clickListener
    }

    fun bind(movie: Movie) {
        binding.movie = movie
        binding.executePendingBindings()
    }
}
