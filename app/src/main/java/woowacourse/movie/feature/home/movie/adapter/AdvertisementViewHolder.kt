package woowacourse.movie.feature.home.movie.adapter

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdvertisementViewHolder(private val binding: ItemAdvertisementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.advertisementViewHolder = this
    }

    fun bind(advertisementLink: String) {
        binding.advertisementImageId = R.drawable.advertisement
        binding.advertisementLink = advertisementLink
    }

    fun advertisementImageClick(advertisementLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(advertisementLink))
        itemView.context.startActivity(intent)
    }
}
