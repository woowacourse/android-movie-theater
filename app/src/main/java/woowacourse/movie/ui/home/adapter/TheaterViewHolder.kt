package woowacourse.movie.ui.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.ui.home.TheaterActionHandler

class TheaterViewHolder(
    private val binding: HolderTheaterBinding,
    private val theaterActionHandler: TheaterActionHandler,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theater: Theater,
        screen: Screen,
    ) {
        binding.screen = screen
        binding.theater = theater
        binding.theaterActionHandler = theaterActionHandler
    }
}
