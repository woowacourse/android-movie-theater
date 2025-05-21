package woowacourse.movie.view.home.movies.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.view.home.theater.OnTheaterEventListener
import woowacourse.movie.view.home.theater.Showing

class TheaterViewHolder(
    eventListener: OnTheaterEventListener,
    val binding: TheaterItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    init {
        binding.onTheaterEventListener = eventListener
    }

    fun bind(showing: Showing) {
        binding.showing = showing
    }
}
