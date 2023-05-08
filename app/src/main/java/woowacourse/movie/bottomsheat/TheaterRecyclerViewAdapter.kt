package woowacourse.movie.bottomsheat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.Theater
import woowacourse.movie.movie.Movie

class TheaterRecyclerViewAdapter(
    private val movie: Movie,
    private val theaters: List<Theater>,
    private val listener: (Int) -> Unit,

) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_theater_list, parent, false)
        return TheaterRecyclerViewHolder(movie, view, listener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = theaters[position]
        (holder as TheaterRecyclerViewHolder).bind(item)
    }

    override fun getItemCount(): Int = theaters.size
}
