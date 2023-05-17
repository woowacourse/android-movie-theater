package woowacourse.movie.presentation.view.main.home

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.presentation.model.Movie

class MovieListAdapter(
    private val movies: List<Movie>,
    private val ad: Drawable,
    private val clickEvent: (Movie) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (ViewType.values()[viewType]) {
            ViewType.AD -> AdListViewHolder(parent)
            ViewType.MOVIE -> {
                val viewHolder = MovieListViewHolder(parent){
                    clickEvent(movies[it])
                }
                viewHolder
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isAdCondition(position)) ViewType.AD.ordinal else ViewType.MOVIE.ordinal
    }

    override fun getItemCount() = (movies.size) + (movies.size / AD_PER_ROW)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isAdCondition(position)) {
            (holder as AdListViewHolder).bind(ad)
            return
        }
        (holder as MovieListViewHolder).bind(movies[position - (position / AD_PER_ROW)])
    }

    private fun isAdCondition(position: Int) =
        (position + 1) % (AD_PER_ROW + 1) == 0 && position > 0

    companion object {
        const val AD_PER_ROW = 3
    }
}
