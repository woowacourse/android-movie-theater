package woowacourse.movie.ui.reservation

import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.DateTimeFormatters

class MovieInfo(private val binding: ActivityMovieDetailBinding) {
    fun setMovieState(movie: MovieState) {
        binding.detailImage.setImageResource(movie.imgId)
        binding.detailTitle.text = movie.title
        binding.detailDate.text =
            DateTimeFormatters.convertToDateTildeDate(
                binding.root.context,
                movie.startDate,
                movie.endDate
            )
        binding.detailTime.text = binding.root.context.getString(
            R.string.running_time,
            movie.runningTime
        )
        binding.description.text = movie.description
    }
}
