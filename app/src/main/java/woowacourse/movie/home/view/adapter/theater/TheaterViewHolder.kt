package woowacourse.movie.home.view.adapter.theater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.Theater

class TheaterViewHolder(private val binding: ItemTheaterBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var theaterName: String = ""
    var screeningTimeNumber: Int = 0
    lateinit var onTheaterItemClick: (Int) -> Unit

    init {
        binding.theaterViewHolder = this
    }

    fun bind(
        theater: Theater,
        onTheaterItemClick: (Int) -> Unit,
    ) {
        theaterName = binding.root.resources.getString(R.string.theater, theater.name)
        screeningTimeNumber = theater.screeningTimes.size
        this.onTheaterItemClick = onTheaterItemClick
    }

    fun theaterItemClick() {
        onTheaterItemClick(bindingAdapterPosition)
    }
}
