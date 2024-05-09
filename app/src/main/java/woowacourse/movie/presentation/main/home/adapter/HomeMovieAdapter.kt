package woowacourse.movie.presentation.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.AdvertisementItemBinding
import woowacourse.movie.databinding.MovieItemBinding
import woowacourse.movie.domain.model.home.HomeItem

class HomeMovieAdapter(
    private val items: List<HomeItem>,
    private val onMovieItemClick: OnMovieItemClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int = when (items[position]) {
        is HomeItem.MovieItem -> VIEW_TYPE_MOVIE
        is HomeItem.AdvertisementItem -> VIEW_TYPE_ADVERTISEMENT
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            VIEW_TYPE_MOVIE -> {
                val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                MovieViewHolder(binding)
            }
            VIEW_TYPE_ADVERTISEMENT -> {
                val binding = AdvertisementItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                AdvertisementViewHolder(binding)
            }
            else -> throw IllegalArgumentException("알 수 없는 정보 $viewType")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = items[position]) {
            is HomeItem.MovieItem -> (holder as MovieViewHolder).bind(item, onMovieItemClick)
            is HomeItem.AdvertisementItem -> (holder as AdvertisementViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    companion object {
        const val VIEW_TYPE_MOVIE = 0
        const val VIEW_TYPE_ADVERTISEMENT = 1
    }
}
