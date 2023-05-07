package woowacourse.movie.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.TheaterMovie

class TheaterAdapter(
    val theaterMovies: List<TheaterMovie>,
    val onClickItem: (data: TheaterMovie) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recyclerview_theater_item, parent, false)
        return TheaterViewHolder(view) { onClickItem(theaterMovies[it]) }
    }

    override fun getItemCount(): Int {
        return theaterMovies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TheaterViewHolder).bind(theaterMovies[position])
    }
}
