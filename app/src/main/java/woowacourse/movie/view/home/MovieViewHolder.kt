package woowacourse.movie.view.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.movie.Movie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    private val onMovieClick: (movieId: Long) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Movie) {
        binding.movie = item
        binding.btnMovieReservation.setOnClickListener {
            onMovieClick.invoke(item.id)
        }
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
