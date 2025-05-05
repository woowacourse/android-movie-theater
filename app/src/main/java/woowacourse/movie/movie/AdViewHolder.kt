package woowacourse.movie.movie

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdItemBinding

class AdViewHolder(
    private val binding: AdItemBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bindAd(movieClickListener: MovieClickListener) {
        binding.movieClickListener = movieClickListener
    }
}
