package woowacourse.movie.ui.fragment.movielist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.ui.model.TheaterModel

class TheaterAdapter(
    private val theaters: List<TheaterModel>,
    private val onTheaterItemClick: (TheaterModel) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val onTheaterItemViewClick: (Int) -> Unit = { onTheaterItemClick(theaters[it]) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.theater_item, parent, false)
        return TheaterViewHolder(view, onTheaterItemViewClick)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TheaterViewHolder -> holder.bind(theaters[position])
        }
    }
}
