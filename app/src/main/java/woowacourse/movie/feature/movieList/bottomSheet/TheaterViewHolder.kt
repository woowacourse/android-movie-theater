package woowacourse.movie.feature.movieList.bottomSheet

import androidx.databinding.ViewDataBinding
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterLayoutBinding
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder

class TheaterViewHolder(
    binding: ViewDataBinding
) : CommonItemViewHolder(binding) {
    override fun bind(itemModel: CommonItemModel) {
        itemModel as TheaterItemModel
        binding as ItemTheaterLayoutBinding
        binding.itemModel = itemModel
        binding.theaterTimeCount.text = binding.root.context.getString(
            R.string.theater_screening_times,
            itemModel.theaterScreening.screeningInfos.first().screeningDateTimes.size
        )
    }
}
