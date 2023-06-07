package woowacourse.movie.presentation.activities.main.fragments.home.viewholder

import android.view.ViewGroup
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Movie

class MovieViewHolder(
    val parent: ViewGroup,
    onClick: (Int) -> Unit,
) : BaseViewHolder(parent, R.layout.item_movie) {
    private val binding = ItemMovieBinding.bind(itemView)

    init {
        binding.bookBtn.setOnClickListener { onClick(adapterPosition) }
    }

    override fun bind(item: ListItem) {
        binding.movie = item as Movie
    }
}
