package woowacourse.movie.fragment.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowacourse.domain.ViewType
import woowacourse.movie.databinding.ItemAdListBinding
import woowacourse.movie.databinding.ItemMovieListBinding
import woowacourse.movie.model.AdUIModel
import woowacourse.movie.model.MovieUIModel

class MovieRecyclerViewAdapter(
    private val movies: List<MovieUIModel>,
    private val ad: AdUIModel,
    private val movieOnItemClicked: (Int) -> Unit,
    private val adOnItemClicked: (AdUIModel) -> Unit,
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.MOVIE -> {
                val binding =
                    ItemMovieListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                MovieRecyclerViewHolder(binding, movieOnItemClicked)
            }
            ViewType.AD -> {
                val binding =
                    ItemAdListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
                AdViewHolder(binding, adOnItemClicked, ad)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = movies[position]
        when (holder.itemViewType) {
            ViewType.MOVIE.ordinal -> (holder as MovieRecyclerViewHolder).bind(item)
            ViewType.AD.ordinal -> (holder as AdViewHolder).bind(ad)
        }
    }

    override fun getItemCount() = movies.size

    override fun getItemViewType(position: Int): Int =
        if ((position + 1) % DIVIDE_ONE_GROUP == 0) ViewType.AD.ordinal else ViewType.MOVIE.ordinal

    companion object {
        private const val DIVIDE_ONE_GROUP = 4
    }
}
