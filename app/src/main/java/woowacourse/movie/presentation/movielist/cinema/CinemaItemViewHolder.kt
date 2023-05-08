package woowacourse.movie.presentation.movielist.cinema

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.CinemaListItemBinding
import woowacourse.movie.presentation.model.CinemaModel

class CinemaItemViewHolder(
    val binding: CinemaListItemBinding,
    clickBook: (CinemaModel) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var _cinemaModel: CinemaModel? = null
    private val cinemaModel get() = _cinemaModel!!

    init {
        binding.imageCinemaArrow.setOnClickListener { clickBook(cinemaModel) }
    }

    fun bind(cinemaModel: CinemaModel) {
        _cinemaModel = cinemaModel
        binding.cinema = cinemaModel
    }
}
