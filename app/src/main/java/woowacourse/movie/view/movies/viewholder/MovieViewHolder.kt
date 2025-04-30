package woowacourse.movie.view.movies.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.view.movies.model.UiModel.MovieUiModel

class MovieViewHolder(
    private val binding: MovieItemBinding,
    onClickBooking: (Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    private var mId: Int = NO_ID
    private val context = itemView.context

    init {
        binding.btnBooking.setOnClickListener { onClickBooking(mId) }
    }

    fun bind(item: MovieUiModel) {
        with(item) {
            mId = id
            binding.model = item
        }
    }

    companion object {
        private const val NO_ID: Int = -1
    }
}
