package woowacourse.movie.feature.common.viewHolder

import androidx.databinding.ViewDataBinding
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.feature.DateTimeFormatters
import woowacourse.movie.feature.common.itemModel.ItemModel
import woowacourse.movie.feature.common.itemModel.MovieItemModel

class MovieViewHolder(
    binding: ViewDataBinding
) : ItemViewHolder(binding) {

    override fun bind(itemModel: ItemModel) {
        binding as ItemMovieBinding
        itemModel as MovieItemModel

        binding.image.setImageResource(itemModel.movieState.imgId)
        binding.reservationTitle.text = itemModel.movieState.title
        binding.runningDate.text =
            DateTimeFormatters.convertToDateTildeDate(
                binding.runningDate.context,
                itemModel.movieState.startDate,
                itemModel.movieState.endDate
            )
        binding.runningTime.text = binding.runningTime.context.getString(
            R.string.running_time,
            itemModel.movieState.runningTime
        )

        binding.reservation.setOnClickListener { itemModel.onClick(itemModel.movieState) }
    }
}
