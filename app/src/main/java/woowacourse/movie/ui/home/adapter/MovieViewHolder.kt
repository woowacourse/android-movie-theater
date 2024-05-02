package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieContentBinding
import woowacourse.movie.model.movie.MovieContent

class MovieViewHolder(
    private val binding: ItemMovieContentBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movieContent: MovieContent) {
        binding.movieContent = movieContent
    }
}
