package woowacourse.movie.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.ui.model.TheaterUiModel

class TheaterViewHolder(val binding: TheaterItemBinding, val onSelectClick: SelectClickListener) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(theater: TheaterUiModel) {
        binding.theater = theater
        binding.clickListener = onSelectClick
    }
}
