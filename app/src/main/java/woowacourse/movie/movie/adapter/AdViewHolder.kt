package woowacourse.movie.movie.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdBannerItemBinding
import woowacourse.movie.movie.MovieListItem

class AdViewHolder(binding: AdBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val imgBanner: ImageView = binding.imgBanner

    fun binding(item: MovieListItem.AdvertisementItem) {
        imgBanner.setImageResource(item.imageId)
    }
}
