package woowacourse.movie.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieListItemBinding
import woowacourse.movie.ui.model.MovieUiModel

class MovieViewHolder(
    private val binding: MovieListItemBinding,
    private val onReserveClick: ReserveClickListener,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(movie: MovieUiModel) {
        binding.movie = movie
        binding.clickListener = onReserveClick
    }

    companion object {
        fun from(
            parent: ViewGroup,
            clickListener: ReserveClickListener,
        ): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = MovieListItemBinding.inflate(inflater, parent, false)
            return MovieViewHolder(binding, clickListener)
        }
    }
}
