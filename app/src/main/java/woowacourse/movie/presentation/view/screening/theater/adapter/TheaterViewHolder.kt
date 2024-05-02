package woowacourse.movie.presentation.view.screening.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.presentation.uimodel.TheaterUiModel
import woowacourse.movie.presentation.view.screening.theater.TheaterBottomSheetContract

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
