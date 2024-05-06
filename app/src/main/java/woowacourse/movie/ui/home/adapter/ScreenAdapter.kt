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
    override fun getItemViewType(position: Int): Int =
        when {
            ((position + 1) % ADVERTISEMENT_INTERVAL == 0) -> ScreenType.ADVERTISEMENT.id
            else -> ScreenType.SCREEN.id
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val screenType =
            ScreenType.entries.find { it.id == viewType }
                ?: throw IllegalArgumentException("Invalid viewType. viewType: $viewType")

        val inflater = LayoutInflater.from(parent.context)

        return when (screenType) {
            ScreenType.SCREEN -> {
                val binding: HolderScreenBinding = HolderScreenBinding.inflate(inflater, parent, false)
                ScreenViewHolder(onScreenClick, binding)
            }

            ScreenType.ADVERTISEMENT -> {
                val binding: HolderAdvertisementBinding = HolderAdvertisementBinding.inflate(inflater, parent, false)
                AdvertisementViewHolder(binding, onAdClick)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        when (val item = getItem(position)) {
            is ScreenAd.ScreenPreviewUi -> {
                (holder as ScreenViewHolder).bind(item)
            }

            is ScreenAd.Advertisement -> {
                (holder as AdvertisementViewHolder).bind(item)
            }
        }
    }

    companion object {
        private const val ADVERTISEMENT_INTERVAL = 4
    }
}
