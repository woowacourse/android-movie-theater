package woowacourse.movie.view.movies.cinema

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.model.Screening

class CinemaViewHolder(
    val binding: ItemCinemaBinding,
) : ViewHolder(binding.root) {
    fun bind(
        screening: Screening,
        eventListener: OnCinemaSelectionListener,
    ) {
        binding.screening = screening
        binding.eventListener = eventListener
        binding.executePendingBindings()
    }
}
