package woowacourse.movie.home.view.adapter.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.databinding.ItemAdvertisementBinding
import woowacourse.movie.databinding.ItemMovieBinding
import woowacourse.movie.home.view.listener.MovieHomeClickListener

class HomeContentAdapter(
    private val movieHomeClickListener: MovieHomeClickListener,
) : RecyclerView.Adapter<ViewHolder>() {
    private var homeContents: List<HomeContent> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return homeContents[position].viewType
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == ADVERTISEMENT_VIEW_TYPE) {
            val advertisementItemBinding = ItemAdvertisementBinding.inflate(inflater, parent, false)
            return AdvertisementViewHolder(advertisementItemBinding)
        }
        val movieItemBinding = ItemMovieBinding.inflate(inflater, parent, false)
        return MovieViewHolder(movieItemBinding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int,
    ) {
        val content = homeContents[position]
        if (content is HomeContent.Movie && holder is MovieViewHolder) {
            holder.bind(content, movieHomeClickListener)
        }
        if (content is HomeContent.Advertisement && holder is AdvertisementViewHolder) {
            holder.bind(content, movieHomeClickListener)
        }
    }

    override fun getItemCount(): Int {
        return homeContents.size
    }

    fun updateHomeContents(homeContents: List<HomeContent>) {
        this.homeContents = homeContents
    }

    companion object {
        private const val ADVERTISEMENT_VIEW_TYPE = 1
    }
}
