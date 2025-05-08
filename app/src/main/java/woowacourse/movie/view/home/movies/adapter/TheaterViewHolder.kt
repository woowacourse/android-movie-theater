package woowacourse.movie.view.home.movies.adapter

import android.view.View.OnClickListener
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.view.home.theater.OnTheaterEventListener
import woowacourse.movie.view.home.theater.Showing

class TheaterViewHolder(
    eventListener: OnTheaterEventListener,
    val binding: TheaterItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    private var showing: Showing? = null

    init {
        binding.onConfirm = OnClickListener { showing?.let { eventListener.onClickReservation(it) } }
    }

    fun bind(showing: Showing) {
        this.showing = showing
        binding.showing = showing
    }
}
