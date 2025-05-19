package woowacourse.movie.view.movies.cinema

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.ItemCinemaBinding
import woowacourse.movie.domain.model.Screening

class CinemaViewHolder(
    val parent: ViewGroup,
    val binding: ItemCinemaBinding = inflate(parent),
) : ViewHolder(binding.root) {
    fun bind(
        screening: Screening,
        eventListener: OnCinemaSelectionListener,
    ) {
        binding.screening = screening
        binding.eventListener = eventListener
        binding.executePendingBindings()
    }

    companion object {
        fun inflate(parent: ViewGroup): ItemCinemaBinding {
            return ItemCinemaBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        }
    }
}
