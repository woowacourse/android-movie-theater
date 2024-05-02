package woowacourse.movie.presentation.homefragments.movieList.viewholder

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdBinding

class AdViewHolder(private val binding: ItemAdBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.ivAdContent.setImageResource(R.drawable.ad1)
    }
}
