package woowacourse.movie.ui.movielist.view

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.theater.Theater

class TheaterViewHolder(
    private val itemBinding: TheaterItemBinding,
    val onClickTheater: (Theater) -> Unit,
) : RecyclerView.ViewHolder(itemBinding.root) {
    private var currentTheater: Theater? = null

    init {
        itemBinding.layoutTheaterItem.setOnClickListener {
            currentTheater?.let { onClickTheater(it) }
        }
    }

    fun bind(theater: Theater) {
        currentTheater = theater
        itemBinding.theater = theater
    }
}
