package woowacourse.movie.home.view.adapter.movie

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.model.Advertisement

class AdvertisementViewHolder(private val binding: ItemAdvertisementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.advertisementViewHolder = this
    }

    fun bind(advertisement: Advertisement) {
        binding.advertisementImageId = advertisement.banner
        binding.advertisementLink = advertisement.link
    }

    fun advertisementImageClick(advertisementLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(advertisementLink))
        itemView.context.startActivity(intent)
    }
}
