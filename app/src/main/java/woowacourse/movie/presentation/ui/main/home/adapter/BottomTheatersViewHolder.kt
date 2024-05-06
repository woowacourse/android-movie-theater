package woowacourse.movie.presentation.ui.main.home.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.presentation.ui.main.home.bottom.BottomTheaterActionHandler

class BottomTheatersViewHolder(
    val binding: HolderTheaterBinding,
    private val actionHandler: BottomTheaterActionHandler,
    val movieId: Int,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(theaterCount: TheaterCount) {
        binding.theaterCount = theaterCount
        binding.handler = actionHandler
        binding.movieId = movieId
    }
}
