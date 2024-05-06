package woowacourse.movie.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.HolderAdsBinding
import woowacourse.movie.databinding.HolderScreenBinding
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.ScreenView.Ads
import woowacourse.movie.domain.model.ScreenView.Movie
import woowacourse.movie.presentation.ui.main.home.ScreenActionHandler
import woowacourse.movie.presentation.ui.main.home.adapter.ScreenRecyclerViewHolder.AdsViewHolder
import woowacourse.movie.presentation.ui.main.home.adapter.ScreenRecyclerViewHolder.ScreenViewHolder

class ScreenRecyclerViewAdapter(
    private val screenActionHandler: ScreenActionHandler,
    private val screens: MutableList<ScreenView> = mutableListOf(),
) : RecyclerView.Adapter<ViewHolder>() {
    override fun getItemViewType(position: Int): Int {
        return when (screens[position]) {
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
                val inflater = LayoutInflater.from(parent.context)
                val binding = HolderAdsBinding.inflate(inflater, parent, false)
                AdsViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = screens.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        when (holder) {
            is ScreenViewHolder -> holder.bind(screens[position] as Movie)
            is AdsViewHolder -> holder.bind(screens[position] as Ads)
        }
    }

    fun updateScreens(newScreens: List<ScreenView>) {
        screens.clear()
        screens.addAll(newScreens)
        notifyDataSetChanged()
    }

    companion object {
        private const val VIEW_TYPE_SCREEN = 0
        private const val VIEW_TYPE_ADS = 1
    }
}
