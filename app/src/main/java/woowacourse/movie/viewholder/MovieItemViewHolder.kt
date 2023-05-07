package woowacourse.movie.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.data.model.itemmodel.MovieItemModel
import woowacourse.movie.databinding.ItemMovieBinding

class MovieItemViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItemModel) {
        binding.movies = item
    }
}
