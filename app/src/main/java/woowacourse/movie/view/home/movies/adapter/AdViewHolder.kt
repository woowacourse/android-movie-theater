package woowacourse.movie.view.home.movies.adapter

import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding

class AdViewHolder(binding: AdvertisementItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val image: ImageView = binding.imgAdBanner

    fun bind(imageUrl: Int) {
        image.setImageResource(imageUrl)
    }
}
