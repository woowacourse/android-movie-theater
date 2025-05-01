package woowacourse.movie.presentation.movies.adapter

import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdvertisementViewHolder(
    private val binding: ItemAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.adImage =
            BitmapFactory.decodeResource(binding.root.resources, R.drawable.advertisement)
    }
}
