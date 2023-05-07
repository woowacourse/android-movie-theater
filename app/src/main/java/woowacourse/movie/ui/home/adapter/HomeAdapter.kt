package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemAdBinding
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.model.MovieListModel
import woowacourse.movie.model.MovieListModel.AdModel
import woowacourse.movie.model.MovieListModel.MovieModel
import woowacourse.movie.ui.home.HomeItemClickListener
import woowacourse.movie.ui.home.adapter.HomeAdapterViewType.AD
import woowacourse.movie.ui.home.adapter.HomeAdapterViewType.MOVIE
import woowacourse.movie.ui.home.adapter.viewholder.AdvertisementViewHolder
import woowacourse.movie.ui.home.adapter.viewholder.MovieViewHolder

class HomeAdapter(
    private val modelItems: List<MovieListModel>,
    private val onItemClick: HomeItemClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val movieBinding = ItemMovieBinding.inflate(layoutInflater, parent, false)
        val advertisementBinding = ItemAdBinding.inflate(layoutInflater, parent, false)

        return when (HomeAdapterViewType.valueOf(viewType)) {
            MOVIE -> MovieViewHolder(movieBinding, onItemClick::onMovieItemClick)
            AD -> AdvertisementViewHolder(advertisementBinding, onItemClick::onAdItemClick)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (modelItems[position]) {
            is MovieModel -> MOVIE.value
            is AdModel -> AD.value
        }
    }

    override fun getItemCount(): Int = modelItems.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is MovieViewHolder -> holder.onBind(modelItems[position] as MovieModel)
            is AdvertisementViewHolder -> holder.onBind(modelItems[position] as AdModel)
        }
    }
}
