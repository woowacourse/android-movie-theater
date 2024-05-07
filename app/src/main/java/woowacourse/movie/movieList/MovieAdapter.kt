package woowacourse.movie.movieList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.ui.AdItemDisplay
import woowacourse.movie.model.ui.MovieDisplay
import woowacourse.movie.model.ui.MovieItemDisplay

class MovieAdapter(
    private val context: Context,
    private val onClick: (Int) -> Unit,
) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {
    private var items: List<MovieDisplay> = emptyList()

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val layoutInflater = LayoutInflater.from(context)

        return when (viewType) {
            MovieItemDisplay.ITEM_VIEW_TYPE_MOVIE -> {
                val view = layoutInflater.inflate(R.layout.item_movie_list, parent, false)
                MovieItemViewHolder(view, onClick)
            }

            AdItemDisplay.ITEM_VIEW_TYPE_AD -> {
                val view = layoutInflater.inflate(R.layout.item_ad, parent, false)
                AdItemViewHolder(view)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        when (holder) {
            is MovieItemViewHolder -> holder.bind(items[position] as MovieItemDisplay)
            is AdItemViewHolder -> holder.bind(items[position] as AdItemDisplay)
        }
    }

    override fun getItemCount(): Int {
        val originalCount = items.size
        return originalCount + originalCount / 3
    }

    fun updateItems(displayData: List<MovieDisplay>) {
        items = displayData
    }

    sealed class MovieViewHolder(view: View) : RecyclerView.ViewHolder(view)
    class MovieItemViewHolder(view: View, private val onClick: (Int) -> Unit) :
        MovieViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.movie_title)
        private val releaseDateTextView: TextView = view.findViewById(R.id.movie_release_date)
        private val durationTextView: TextView = view.findViewById(R.id.movie_duration)
        private val detailButton: Button = view.findViewById(R.id.movie_details_button)

        fun bind(movie: MovieItemDisplay) {
            titleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate
            durationTextView.text = movie.runningTime
            detailButton.setOnClickListener {
                onClick(absoluteAdapterPosition)
            }
        }
    }

    class AdItemViewHolder(view: View) : MovieViewHolder(view) {
        private val adView: ImageView = view.findViewById(R.id.img_ad)
        fun bind(ad: AdItemDisplay) {
            adView.setImageResource(ad.poster)
        }
    }
}
