package woowacourse.movie.presentation.homefragments.movieList.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.Movie
import woowacourse.movie.presentation.homefragments.movieList.listener.MovieListClickListener

class MovieViewHolder(private val binding: ItemMovieBinding, val listener: MovieListClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: Movie) {
        binding.movie = movie
        binding.screeningPeriod = movie.screeningPeriodToString()
        binding.listener = listener
    }

    private fun Movie.screeningPeriodToString(): String {
        return "${screeningDates.startDate} ~ ${screeningDates.endDate}"
    }
}
