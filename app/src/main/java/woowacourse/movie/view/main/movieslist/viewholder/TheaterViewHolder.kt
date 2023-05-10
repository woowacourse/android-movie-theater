package woowacourse.movie.view.main.movieslist.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.TheaterUiModel

class TheaterViewHolder(private val binding: ItemTheaterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theaterUiModel: TheaterUiModel,
        onClickEvent: (TheaterUiModel) -> Unit,
    ) {
        itemView.setOnClickListener { onClickEvent(theaterUiModel) }
        binding.itemTheaterName.text =
            binding.itemTheaterName.context.getString(R.string.theater_name_format)
                .format(theaterUiModel.name)
        binding.itemTheaterTimeCount.text =
            binding.itemTheaterTimeCount.context.getString(R.string.theater_time_count_format)
                .format(theaterUiModel.screenTimes.size)
    }
}
