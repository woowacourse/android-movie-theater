package woowacourse.movie.ui.movielist.view

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.Theater
import woowacourse.movie.ui.movielist.view.listener.TheaterClickListener

class TheaterViewHolder(
    private val movieId: Long,
    private val itemBinding: TheaterItemBinding,
    theaterClickListener: TheaterClickListener,
) : RecyclerView.ViewHolder(itemBinding.root) {
    init {
        itemBinding.theaterClickListener = theaterClickListener
    }

    fun bind(theater: Theater) {
        itemBinding.movieId = movieId
        itemBinding.theater = theater
    }
}
