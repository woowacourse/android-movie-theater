package woowacourse.movie.movie.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieListItemBinding
import woowacourse.movie.ui.model.MovieUiModel

class MovieViewHolder(val binding: MovieListItemBinding, val onReserveClick: ReserveClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieUiModel) {
        binding.movie = movie
        binding.clickListener = onReserveClick
    }
}
