package woowacourse.movie.view.moviemain.movielist

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.model.MovieListModel.MovieUiModel

class MovieItemViewHolder(
    private val binding: MovieItemBinding,
    private val onViewClick: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.reserveNowButton.setOnClickListener {
            onViewClick(adapterPosition)
        }
    }

    fun bind(movie: MovieUiModel) {
        binding.movieItem = movie
    }
}
