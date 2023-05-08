package woowacourse.movie.presentation.movielist.movie.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieListItemBinding
import woowacourse.movie.presentation.model.MovieModel
import woowacourse.movie.presentation.movielist.movie.MovieItem

class MovieViewHolder(
    private val binding: MovieListItemBinding,
    clickBook: (MovieModel) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    private var _movie: MovieModel? = null
    private val movie get() = _movie!!

    init {
        binding.buttonItemBook.setOnClickListener { clickBook(movie) }
    }

    fun bind(item: MovieItem.Movie) {
        _movie = item.movie
        binding.movie = movie
    }
}
