package woowacourse.movie.presentation.screening.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.presentation.screening.theater.TheaterBottomSheetContract
import woowacourse.movie.presentation.uimodel.TheaterUiModel

class TheaterViewHolder(private val binding: TheaterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theater: TheaterUiModel,
        clickListener: TheaterBottomSheetContract.ItemListener,
    ) {
        binding.data = theater
        binding.listener = clickListener
    }
}
