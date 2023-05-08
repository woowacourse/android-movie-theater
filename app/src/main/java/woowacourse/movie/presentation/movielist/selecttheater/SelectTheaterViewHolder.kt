package woowacourse.movie.presentation.movielist.selecttheater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.SelectTheaterBottomSheetItemBinding

class SelectTheaterViewHolder(
    private val binding: SelectTheaterBottomSheetItemBinding,
    clickBook: (String) -> Unit,
) :
    RecyclerView.ViewHolder(binding.root) {

    private lateinit var currentItem: String

    init {
        binding.root.setOnClickListener { clickBook(currentItem) }
    }

    fun bind(theaterName: String, timeTableCount: Int) {
        currentItem = theaterName
        binding.movieTheaterName = theaterName
        binding.timeTableCount = timeTableCount
    }
}
