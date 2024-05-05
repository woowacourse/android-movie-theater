package woowacourse.movie.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.HolderAdsBinding
import woowacourse.movie.databinding.HolderScreenBinding
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.ScreenView.Ads
import woowacourse.movie.domain.model.ScreenView.Movie
import woowacourse.movie.presentation.ui.main.home.ScreenActionHandler
import woowacourse.movie.presentation.ui.main.home.adapter.ScreenRecyclerViewHolder.AdsViewHolder
import woowacourse.movie.presentation.ui.main.home.adapter.ScreenRecyclerViewHolder.ScreenViewHolder
import woowacourse.movie.presentation.utils.ItemDiffCallback

class ScreenRecyclerViewAdapter(
    private val screenActionHandler: ScreenActionHandler,
) : ListAdapter<ScreenView, ViewHolder>(ScreenAdapterDiffCallback) {
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getItemViewType(position: Int): Int {
        return when (currentList[position]) {
            is Movie -> VIEW_TYPE_SCREEN
            is Ads -> VIEW_TYPE_ADS
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_SCREEN -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderScreenBinding.inflate(inflater, parent, false)
                ScreenViewHolder(binding, screenActionHandler)
            }

            VIEW_TYPE_ADS -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderAdsBinding.inflate(inflater, parent, false)
                AdsViewHolder(binding)
            }

            else -> {
                throw IllegalArgumentException("잘못된 viewType입니다.")
            }
        }
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ScreenViewHolder -> holder.bind(currentList[position] as Movie)
            is AdsViewHolder -> holder.bind(currentList[position] as Ads)
        }
    }

    companion object {
        private const val VIEW_TYPE_SCREEN = 0
        private const val VIEW_TYPE_ADS = 1
        private val ScreenAdapterDiffCallback =
            ItemDiffCallback<ScreenView>(
                onItemsTheSame = { old, new -> old.id() == new.id() },
                onContentsTheSame = { old, new -> old == new },
            )
    }
}
