package woowacourse.movie.home.view.adapter.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.home.view.adapter.theater.listener.TheaterClickListener
import woowacourse.movie.model.Theater

class TheaterViewHolder(private val binding: ItemTheaterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(
        theater: Theater,
        theaterClickListener: TheaterClickListener,
    ) {
        binding.theaterClickListener = theaterClickListener
        binding.theaterPosition = bindingAdapterPosition
        binding.theaterName = binding.root.resources.getString(R.string.theater, theater.name)
        binding.screeningTimeCount = theater.screeningTimes.size
    }
}
