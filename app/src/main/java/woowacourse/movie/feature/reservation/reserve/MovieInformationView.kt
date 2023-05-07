package woowacourse.movie.feature.reservation.reserve

import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.feature.DateTimeFormatters
import woowacourse.movie.model.MovieState

class MovieInformationView(
    private val binding: ActivityMovieDetailBinding
) {
    fun setContents(movie: MovieState) {
        binding.movieImage.setImageResource(movie.imgId)
        binding.movieTitle.text = movie.title
        binding.movieScreeningPeriod.text =
            DateTimeFormatters.convertToDateTildeDate(
                binding.root.context,
                movie.startDate,
                movie.endDate
            )
        binding.movieRunningTime.text =
            binding.root.context.getString(R.string.running_time, movie.runningTime)
        binding.movieDescription.text = movie.description
    }
}
