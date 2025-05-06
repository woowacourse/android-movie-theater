package woowacourse.movie.feature.home.view.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R

class AdvertisementViewHolder(
    private val view: View,
) : RecyclerView.ViewHolder(view) {
    fun bind(advertisement: ContentItem.Advertisement) {
        view.findViewById<View>(R.id.iv_advertisement).setBackgroundResource(advertisement.value.image)
    }
}
