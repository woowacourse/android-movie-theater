package woowacourse.movie.feature.movieList.bottomSheet

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterLayoutBinding

class TheaterViewHolder(
    private val binding: ItemTheaterLayoutBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(itemModel: TheaterItemModel) {
        binding.itemModel = itemModel
        binding.theaterTimeCount.text = binding.root.context.getString(
            R.string.theater_screening_times,
            itemModel.theaterScreening.screeningInfos.first().screeningDateTimes.size
        )
    }
}
