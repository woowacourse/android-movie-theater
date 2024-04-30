package woowacourse.movie.main.view.adapter.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.Theater

class TheaterViewHolder(private val binding: ItemTheaterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(theater: Theater, onTheaterItemClick: () -> Unit) {
        binding.theaterName.text = theater.name
        binding.screeningTimeNumber.text = theater.screeningTimes.size.toString()
        binding.root.setOnClickListener {
            onTheaterItemClick()
        }
    }
}