package woowacourse.movie.presentation.views.main.fragments.home.recyclerview

import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.presentation.base.BaseRecyclerView
import woowacourse.movie.presentation.model.movieitem.Movie

class MovieViewHolder(
    private val binding: ItemMovieBinding,
    onClick: (Int) -> Unit,
) : BaseRecyclerView.BaseViewHolder(binding.root) {

    init {
        binding.bookBtn.setOnClickListener { onClick(adapterPosition) }
    }

    override fun <T> bind(item: T) {
        binding.movie = (item as? Movie) ?: return
    }
}
