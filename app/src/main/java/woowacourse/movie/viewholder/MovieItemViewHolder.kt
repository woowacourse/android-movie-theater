package woowacourse.movie.viewholder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.model.itemmodel.MovieItemModel
import woowacourse.movie.databinding.ItemMovieBinding

class MovieItemViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.item_movie, parent, false)
) {
    private val binding = ItemMovieBinding.bind(itemView)

    fun bind(item: MovieItemModel) {
        binding.movies = item
    }
}
