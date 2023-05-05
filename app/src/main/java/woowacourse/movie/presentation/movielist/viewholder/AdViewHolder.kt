package woowacourse.movie.presentation.movielist.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieListAdItemBinding
import woowacourse.movie.presentation.movielist.MovieItem

class AdViewHolder(private val viewBinding: MovieListAdItemBinding) :
    RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: MovieItem.Ad) {
        viewBinding.imageMainAd.setImageResource(item.adImage)
    }
}
