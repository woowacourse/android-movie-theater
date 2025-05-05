package woowacourse.movie.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.view.mapper.Formatter.localDateToUi

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onMovieClick: (movieId: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onMovieClick = onMovieClick
    }

    fun bind(item: Movie) {
        val startDate: String = localDateToUi(item.startDate)
        val endDate: String = localDateToUi(item.endDate)
        binding.movie = item
        binding.tvMovieScreeningDate.text =
            binding.root.resources.getString(R.string.movie_screening_date, startDate, endDate)
        binding.executePendingBindings()
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onMovieClick: (movieId: Long) -> Unit,
        ): MovieViewHolder =
            MovieViewHolder(
                ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onMovieClick,
            )
    }
}
