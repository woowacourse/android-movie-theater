package woowacourse.movie.view.movies

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.domain.model.Movie
import java.time.format.DateTimeFormatter

class MovieViewHolder(
    val view: View,
    val binding: ItemMovieBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: Movie,
        eventListener: OnMovieEventListener,
    ) {
        binding.movie = movie
        binding.ivPoster.setImageResource(movie.poster.toInt())
        binding.btnReservation.setOnClickListener {
            eventListener.onReserveButtonClick(movie)
        }
        binding.executePendingBindings()

    }
}
