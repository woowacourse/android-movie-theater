package woowacourse.movie.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieListItemBinding
import woowacourse.movie.ui.model.MovieUiModel

class MovieViewHolder(
    parent: ViewGroup,
    private val onReserveClick: ReserveClickListener,
) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.movie_list_item, parent, false),
    ) {
    private val binding = MovieListItemBinding.bind(itemView)

    fun bind(movie: MovieUiModel) {
        binding.movie = movie
        binding.clickListener = onReserveClick
    }
}
