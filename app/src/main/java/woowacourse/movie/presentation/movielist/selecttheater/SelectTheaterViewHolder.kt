package woowacourse.movie.presentation.movielist.selecttheater

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.SelectTheaterBottomSheetItemBinding

class SelectTheaterViewHolder(
    private val binding: SelectTheaterBottomSheetItemBinding,
    clickBook: (String) -> Unit,
    getItem: (Int) -> String
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener { clickBook(getItem(layoutPosition)) }
    }

    fun bind(theaterName: String, timeTableCount: Int) {
        binding.movieTheaterName = theaterName
        binding.timeTableCount = timeTableCount
    }
}
