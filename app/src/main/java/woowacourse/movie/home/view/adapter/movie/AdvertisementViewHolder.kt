package woowacourse.movie.home.view.adapter.movie

import android.content.Intent
import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdvertisementViewHolder(binding: ItemAdvertisementBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var advertisementImage: Int = 0
    private var advertisementLink: String = ""

    init {
        binding.advertisementViewHolder = this
    }

    fun bind(advertisementLink: String) {
        advertisementImage = R.drawable.advertisement
        this.advertisementLink = advertisementLink
    }

    fun advertisementImageClick() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(advertisementLink))
        itemView.context.startActivity(intent)
    }
}
