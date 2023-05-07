package woowacourse.movie.fragment.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.woowacourse.domain.Theater
import woowacourse.movie.R

class TheaterRecyclerViewAdapter(
    private val theaters: List<Theater>,
    private val theaterOnClickListener: (Int) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_theater_list, parent, false)
        return TheaterViewHolder(view, theaterOnClickListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = theaters[position]
        (holder as TheaterViewHolder).bind(item)
    }

    override fun getItemCount(): Int = theaters.size
}
