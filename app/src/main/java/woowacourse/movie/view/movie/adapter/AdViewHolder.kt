package woowacourse.movie.view.movie.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemAdvertisementBinding

class AdViewHolder(
    private val binding: ItemAdvertisementBinding,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind() {
        binding.ivAdvertisement.setImageResource(R.drawable.advertisement)
    }

    companion object {
        fun from(parent: ViewGroup): AdViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAdvertisementBinding.inflate(layoutInflater, parent, false)
            return AdViewHolder(binding)
        }
    }
}
