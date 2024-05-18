package woowacourse.movie.presentation.screening.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.presentation.uimodel.MovieUiModel

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val buttonClickedListener: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.reserveButton.setOnClickListener { buttonClickedListener(adapterPosition) }
    }

    fun bind(movie: MovieUiModel) {
        binding.movie = movie
    }
}
