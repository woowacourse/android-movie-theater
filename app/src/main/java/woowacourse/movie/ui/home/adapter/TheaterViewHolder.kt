package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.ui.home.TheaterAdapterActionHandler

class TheaterViewHolder(
    private val binding: HolderTheaterBinding,
    private val theaterAdapterActionHandler: TheaterAdapterActionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theater: Theater,
        screen: Screen,
    ) {
        binding.screen = screen
        binding.theater = theater
        binding.theaterAdapterHandler = theaterAdapterActionHandler
    }
}
