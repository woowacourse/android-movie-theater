package woowacourse.movie.view.moviemain.movielist

import android.view.View
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieAdItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.util.DATE_FORMATTER
import woowacourse.movie.view.model.MovieUiModel

sealed class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    class MovieItemViewHolder(private val binding: MovieItemBinding) : ItemViewHolder(binding.root) {
        fun set(movie: MovieUiModel, clickListener: View.OnClickListener) {
            val context = binding.movieTitle.context
            binding.moviePoster.setImageResource(movie.posterResourceId)
            binding.movieTitle.text = movie.title
            binding.movieScreeningDate.text =
                context.getString(R.string.screening_date_format).format(
                    movie.startDate.format(DATE_FORMATTER),
                    movie.endDate.format(DATE_FORMATTER),
                )

            binding.movieRunningTime.text =
                context.getString(R.string.running_time_format, movie.runningTime)
            binding.reserveNowButton.setOnClickListener(clickListener)
        }
    }

    class AdItemViewHolder(private val binding: MovieAdItemBinding) : ItemViewHolder(binding.root) {
        fun set(@DrawableRes resId: Int) {
            binding.adImageview.setImageResource(resId)
        }
    }
}
