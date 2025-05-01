package woowacourse.movie.presentation.theater.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.common.adapter.ClickListener
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.domain.model.Screening

class TheaterViewHolder(
    private val binding: ItemTheaterBinding,
    private val onClickTheater: (Screening) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Screening) {
        binding.screeningInfo = item
        binding.handler = ClickListener<Screening> { onClickTheater(it) }
        binding.executePendingBindings()
    }
}
