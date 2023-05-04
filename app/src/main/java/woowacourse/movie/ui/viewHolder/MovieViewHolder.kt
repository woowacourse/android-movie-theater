package woowacourse.movie.ui.viewHolder

import woowacourse.movie.databinding.ItemMovieLayoutBinding
import woowacourse.movie.ui.itemModel.ItemModel
import woowacourse.movie.ui.itemModel.MovieItemModel

class MovieViewHolder(
    private val binding: ItemMovieLayoutBinding,
    onClick: (position: Int) -> Unit
) : ItemViewHolder(binding.root) {
    init {
        binding.reservation.setOnClickListener { onClick(bindingAdapterPosition) }
    }

    override fun bind(itemModel: ItemModel) {
        val item = itemModel as MovieItemModel
        binding.movie = item.movieState
    }
}
