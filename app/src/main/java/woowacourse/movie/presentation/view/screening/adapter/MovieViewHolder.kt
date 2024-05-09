package woowacourse.movie.presentation.view.screening.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.presentation.uimodel.MovieUiModel
import woowacourse.movie.presentation.view.screening.ScreeningContract

class MovieViewHolder(private val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        movie: MovieUiModel,
        itemClickListener: ScreeningContract.ViewActions,
    ) {
        binding.data = movie
        binding.listener = itemClickListener
    }
}
