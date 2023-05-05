package woowacourse.movie.feature.movieList.bottomSheet

import android.widget.TextView
import androidx.databinding.BindingAdapter
import woowacourse.movie.databinding.ItemTheaterLayoutBinding
import woowacourse.movie.feature.common.itemModel.CommonItemModel
import woowacourse.movie.feature.common.viewHolder.CommonItemViewHolder
import woowacourse.movie.model.TheaterState

class TheaterViewHolder(
    binding: ItemTheaterLayoutBinding
) : CommonItemViewHolder(binding) {
    override fun bind(itemModel: CommonItemModel) {
        val binding = binding as ItemTheaterLayoutBinding
        binding.theater = itemModel as TheaterItemModel
    }
}

@BindingAdapter("theater_time_count_text")
fun setTheaterTimeCountText(view: TextView, theater: TheaterState) {
    view.text = "${theater.screenInfos.first().dateTimes.size}개의 상영시간"
}
