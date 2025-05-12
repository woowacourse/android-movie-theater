package woowacourse.movie.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.BottomSheetItemBinding
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater

class TheaterViewHolder(private val binding: BottomSheetItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun setItem(item: Theater, movie: Movie, onClicked: (Theater) -> Unit) {
        binding.theater = item
        binding.movie = movie
        itemView.setOnClickListener {
            onClicked(item)
        }
    }
}

