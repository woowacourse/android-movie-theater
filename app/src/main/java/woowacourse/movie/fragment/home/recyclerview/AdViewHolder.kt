package woowacourse.movie.fragment.home.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdItemBinding
import woowacourse.movie.dto.movie.AdUIModel
import woowacourse.movie.util.listener.OnClickListener

class AdViewHolder private constructor(
    private val binding: AdItemBinding,
    onItemClickListener: OnClickListener<Int>,
) :
    RecyclerView.ViewHolder(binding.root) {

    init {
        binding.ad.setOnClickListener {
            onItemClickListener.onClick(absoluteAdapterPosition)
        }
    }

    fun bind(item: AdUIModel) {
        binding.ad.setImageResource(item.adImage)
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onItemClickListener: OnClickListener<Int>,
        ): AdViewHolder {
            val binding = AdItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
            return AdViewHolder(binding, onItemClickListener)
        }
    }
}
