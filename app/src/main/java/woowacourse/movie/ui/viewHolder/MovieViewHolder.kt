package woowacourse.movie.ui.viewHolder

import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieLayoutBinding
import woowacourse.movie.ui.DateTimeFormatters
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
        binding.image.setImageResource(item.movieState.imgId)
        binding.reservationTitle.text = item.movieState.title
        binding.runningDate.text =
            DateTimeFormatters.convertToDateTildeDate(
                binding.root.context,
                item.movieState.startDate,
                item.movieState.endDate
            )
        binding.runningTime.text = binding.root.context.getString(
            R.string.running_time,
            item.movieState.runningTime
        )
    }
}
