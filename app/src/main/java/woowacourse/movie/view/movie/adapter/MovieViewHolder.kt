package woowacourse.movie.view.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.movie.MovieClickListener

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val clickListener: MovieClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
        binding.tvMovieTitle.text = movie.title
        binding.ivMoviePoster.setImageResource(movie.poster)
        binding.tvMovieScreeningDate.text =
            itemView.context.getString(
                R.string.movie_screening_date,
                ReservationUiFormatter.localDateToUI(movie.startDate),
                ReservationUiFormatter.localDateToUI(movie.endDate),
            )
        binding.tvMovieRunningTime.text =
            itemView.context.getString(R.string.movie_running_time, movie.runningTime)
        binding.btnMovieReservation.setOnClickListener { clickListener.onReservationClick(movie) }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            clickListener: MovieClickListener,
        ): MovieViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemMovieBinding.inflate(layoutInflater, parent, false)
            return MovieViewHolder(binding, clickListener)
        }
    }
}
