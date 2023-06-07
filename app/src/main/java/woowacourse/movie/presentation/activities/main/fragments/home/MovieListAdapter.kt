package woowacourse.movie.presentation.activities.main.fragments.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.activities.main.fragments.home.type.MovieViewType
import woowacourse.movie.presentation.activities.main.fragments.home.viewholder.MovieViewHolder
import woowacourse.movie.presentation.activities.main.fragments.home.viewholder.NativeAdViewHolder
import woowacourse.movie.presentation.base.BaseViewHolder
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.ListItem
import woowacourse.movie.presentation.model.item.Movie

class MovieListAdapter(
    private val items: MutableList<ListItem>,
    private val adInterval: Int = DEFAULT_AD_INTERVAL,
    private val adTypes: List<ListItem>,
    private val onItemClick: (ListItem) -> Unit = {},
) : RecyclerView.Adapter<BaseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return when (MovieViewType.get(viewType)) {
            MovieViewType.MOVIE -> {
                MovieViewHolder(parent) { position ->
                    onItemClick(items[position])
                }
            }
            MovieViewType.AD -> {
                NativeAdViewHolder(parent) { position ->
                    onItemClick(items[position])
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is Movie -> MovieViewType.MOVIE.type
        is Ad -> MovieViewType.AD.type
        else -> throw IllegalArgumentException(INVALID_ITEM_TYPE)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun appendAll(newItems: List<ListItem>) {
        var newAdSize = 0
        newItems.forEach { newMovie ->
            newAdSize += appendAd()
            items.add(newMovie)
        }
        notifyItemRangeChanged(items.size, newItems.size + newAdSize)
    }

    private fun appendAd(): Int {
        if ((items.size + 1) % (adInterval + 1) == 0) {
            if (items.add(adTypes.random())) return APPENDED_SIZE
        }
        return NO_APPENDED_SIZE
    }

    companion object {
        private const val DEFAULT_AD_INTERVAL = 3
        private const val APPENDED_SIZE = 1
        private const val NO_APPENDED_SIZE = 0

        private const val INVALID_ITEM_TYPE = "Invalid type"
    }
}
