package woowacourse.movie.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieAdapterViewType
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.view.adapter.viewholder.AdvertisementItemViewHolder
import woowacourse.movie.view.adapter.viewholder.MovieItemViewHolder

class MovieAdapter(
    private val movieUiModels: List<MovieUiModel>,
    private val advertisementUiModel: AdvertisementUiModel,
    private val advertisementClickEvent: (AdvertisementUiModel) -> Unit,
    private val movieListClickEvent: (MovieUiModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (position % CYCLE) {
            ADVERTISEMENT_TURN -> MovieAdapterViewType.ADVERTISEMENT.value
            else -> MovieAdapterViewType.MOVIE.value
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (MovieAdapterViewType.find(viewType)) {
            MovieAdapterViewType.MOVIE -> MovieItemViewHolder(
                inflateView(parent, R.layout.item_movie)
            )
            MovieAdapterViewType.ADVERTISEMENT -> AdvertisementItemViewHolder(
                inflateView(parent, R.layout.item_advertisement)
            )
        }
    }

    private fun inflateView(viewGroup: ViewGroup, layoutId: Int): View {
        return LayoutInflater.from(viewGroup.context).inflate(layoutId, viewGroup, false)
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        if (viewHolder is AdvertisementItemViewHolder) viewHolder.bind(
            advertisementUiModel, advertisementClickEvent
        )
        if (viewHolder is MovieItemViewHolder) viewHolder.bind(
            movieUiModels[position], movieListClickEvent
        )
    }

    override fun getItemCount(): Int {
        return MAXIMUM_RECYCLER_VIEW_COUNT
    }

    companion object {
        private const val ADVERTISEMENT_TURN = 3
        private const val CYCLE = 4
        private const val MAXIMUM_RECYCLER_VIEW_COUNT = 10_000
    }
}
