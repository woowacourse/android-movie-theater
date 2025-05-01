package woowacourse.movie.movie.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdBannerItemBinding

class AdViewHolder(binding: AdBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    val imgBanner: ImageView = binding.imgBanner
}
