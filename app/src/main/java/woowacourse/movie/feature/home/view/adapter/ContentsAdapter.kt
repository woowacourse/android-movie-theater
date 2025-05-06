package woowacourse.movie.feature.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.feature.model.MovieUiModel

class ContentsAdapter(
    private val onBookingClick: (MovieUiModel) -> Unit,
) : ListAdapter<ContentItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (ContentItemViewType.entries[viewType]) {
            ContentItemViewType.MOVIE ->
                MovieViewHolder(
                    inflater.inflate(R.layout.item_movie, parent, false),
                )

            ContentItemViewType.ADVERTISEMENT ->
                AdvertisementViewHolder(
                    inflater.inflate(R.layout.item_advertisement, parent, false),
                )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val contentItem: ContentItem = getItem(position)

        when (holder) {
            is MovieViewHolder -> holder.bind(contentItem as ContentItem.Movie, onBookingClick)
            is AdvertisementViewHolder -> holder.bind(contentItem as ContentItem.Advertisement)
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun submitList(list: List<ContentItem?>?) {
        if (itemCount + (list?.size ?: 0) > MAX_ITEM_COUNT) return else super.submitList(list)
    }

    companion object {
        private const val MAX_ITEM_COUNT = 10000
    }
}
