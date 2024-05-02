package woowacourse.movie.presentation.ui.screen.bottom

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.TheaterCount

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
