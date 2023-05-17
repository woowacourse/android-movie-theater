package woowacourse.movie.fragment.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowacourse.domain.TheaterMovie
import woowacourse.movie.databinding.ItemTheaterListBinding

class TheaterRecyclerViewAdapter(
    private val theaterMovies: List<TheaterMovie>,
    private val theaterOnClickListener: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            ItemTheaterListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding, theaterOnClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = theaterMovies[position]
        (holder as TheaterViewHolder).bind(item)
    }

    override fun getItemCount(): Int = theaterMovies.size
}
