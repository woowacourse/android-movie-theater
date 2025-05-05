package woowacourse.movie.feature.home.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.feature.model.MovieUiModel

class MoviesAdapter(
    private val onBookingClick: (MovieUiModel) -> Unit,
) : ListAdapter<MovieItem, RecyclerView.ViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (MovieItemViewType.entries[viewType]) {
            MovieItemViewType.MOVIE ->
                MovieViewHolder(
                    inflater.inflate(R.layout.item_movie, parent, false),
                )

            MovieItemViewType.ADVERTISEMENT ->
                AdvertisementViewHolder(
                    inflater.inflate(R.layout.item_advertisement, parent, false),
                )
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val movieItem: MovieItem = getItem(position)

        when (holder) {
            is MovieViewHolder -> holder.bind(movieItem as MovieItem.Movie, onBookingClick)
            is AdvertisementViewHolder -> Unit
        }
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    override fun submitList(list: List<MovieItem?>?) {
        if (itemCount + (list?.size ?: 0) > 10000) return else super.submitList(list)
    }
}
