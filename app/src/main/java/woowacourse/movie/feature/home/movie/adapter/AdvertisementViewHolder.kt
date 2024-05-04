package woowacourse.movie.feature.home.movie.adapter

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.feature.home.movie.ui.AdvertisementUiModel

class AdvertisementViewHolder(private val binding: ItemAdvertisementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(advertisementLink: String) {
        binding.advertisement = AdvertisementUiModel(R.drawable.advertisement, advertisementLink, ::advertisementImageClick)
    }

    fun advertisementImageClick(advertisementLink: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(advertisementLink))
        itemView.context.startActivity(intent)
    }
}
