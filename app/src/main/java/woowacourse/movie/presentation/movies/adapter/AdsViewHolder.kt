package woowacourse.movie.presentation.movies.adapter

import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdsBinding

class AdsViewHolder(
    private val binding: ItemAdsBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.image = R.drawable.advertisement
    }
}
