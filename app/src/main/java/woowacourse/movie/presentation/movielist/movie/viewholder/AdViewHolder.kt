package woowacourse.movie.presentation.movielist.movie.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieListAdItemBinding
import woowacourse.movie.presentation.movielist.movie.MovieItem

class AdViewHolder(private val binding: MovieListAdItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: MovieItem.Ad) {
        binding.imageMainAd.setImageResource(item.adImage)
    }
}
