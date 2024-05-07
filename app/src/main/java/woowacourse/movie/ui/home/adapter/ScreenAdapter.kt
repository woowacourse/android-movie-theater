package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderAdvertisementBinding
import woowacourse.movie.databinding.HolderScreenBinding
import woowacourse.movie.domain.model.ScreenAd
import woowacourse.movie.domain.model.ScreenType

class ScreenAdapter(
    private val onScreenClick: (id: Int) -> Unit,
    private val onAdClick: (id: Int) -> Unit,
) : ListAdapter<ScreenAd, RecyclerView.ViewHolder>(ScreenPreviewUiDiffUtil()) {
    private lateinit var inflater: LayoutInflater

    override fun getItemViewType(position: Int): Int =
        when (currentList[position]) {
            is ScreenAd.ScreenPreviewUi -> ScreenType.SCREEN.id
            is ScreenAd.Advertisement -> ScreenType.ADVERTISEMENT.id
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        if (!::inflater.isInitialized) inflater = LayoutInflater.from(parent.context)

        val screenType =
            ScreenType.entries.find { it.id == viewType }
                ?: throw IllegalArgumentException("유효하지 않은 View Type입니다. viewType: $viewType")

        return when (screenType) {
            ScreenType.SCREEN -> {
                val binding: HolderScreenBinding = HolderScreenBinding.inflate(inflater, parent, false)
                ScreenViewHolder(onScreenClick, binding)
            }

            ScreenType.ADVERTISEMENT -> {
                val binding: HolderAdvertisementBinding = HolderAdvertisementBinding.inflate(inflater, parent, false)
                AdvertisementViewHolder(onAdClick, binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = getItem(position)

        if (item is ScreenAd.ScreenPreviewUi && holder is ScreenViewHolder) {
            holder.bind(item)
        } else if (item is ScreenAd.Advertisement && holder is AdvertisementViewHolder) {
            holder.bind(item)
        }
    }
}
