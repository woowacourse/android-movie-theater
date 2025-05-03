package woowacourse.movie.movie.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdBannerItemBinding
import woowacourse.movie.ui.model.MovieFeedUiModel

class AdViewHolder(binding: AdBannerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val imgBanner: ImageView = binding.imgBanner

    fun binding(item: MovieFeedUiModel.AdvertisementItem) {
        imgBanner.setImageResource(item.imageId)
    }
}
