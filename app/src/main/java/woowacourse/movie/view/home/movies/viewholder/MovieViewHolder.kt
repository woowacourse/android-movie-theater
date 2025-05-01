package woowacourse.movie.view.home.movies.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.home.movies.model.UiModel.MovieUiModel

class MovieViewHolder(
    private val binding: MovieItemBinding,
    private val onClickBooking: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: MovieUiModel) {
        with(binding) {
            model = item
            btnBooking.setOnClickListener { onClickBooking(item.id) }
        }
    }
}
