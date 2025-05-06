package woowacourse.movie.ui.movielist.view

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.Theater

class TheaterViewHolder(
    private val movieId: Long,
    private val itemBinding: TheaterItemBinding,
    private val onClickTheater: (Theater) -> Unit,
) : RecyclerView.ViewHolder(itemBinding.root) {
    fun bind(theater: Theater) {
        itemBinding.layoutTheaterItem.setOnClickListener {
            onClickTheater(theater)
        }
        itemBinding.movieId = movieId
        itemBinding.theater = theater
    }
}
