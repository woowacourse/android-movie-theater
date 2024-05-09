package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theater

class TheaterViewHolder(
    private val binding: HolderTheaterBinding,
    private val itemClick: (theaterId: Int) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theater: Theater,
        screen: Screen,
    ) {
        binding.screen = screen
        binding.theater = theater

        binding.next.setOnClickListener {
            itemClick(theater.id)
        }
    }
}