package woowacourse.movie.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.AdBannerItemBinding
import woowacourse.movie.ui.model.MovieFeedUiModel

class AdViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.ad_banner_item, parent, false),
) {
    private val binding = AdBannerItemBinding.bind(itemView)

    private val imgBanner: ImageView = binding.imgBanner

    fun bind(item: MovieFeedUiModel.AdvertisementItem) {
        imgBanner.setImageResource(item.imageId)
    }
}
