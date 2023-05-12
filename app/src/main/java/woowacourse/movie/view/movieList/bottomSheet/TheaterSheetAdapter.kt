package woowacourse.movie.view.movieList.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.model.TheaterModel

class TheaterSheetAdapter(
    private val theaters: List<TheaterModel>,
    private val onTheaterClick: (TheaterModel) -> Unit
) : RecyclerView.Adapter<ViewHolder>() {
    private val onClick: (Int) -> Unit = { onTheaterClick(theaters[it]) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.theater_item, parent, false)
        return TheaterSheetViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is TheaterSheetViewHolder -> holder.bind(theaters[position])
        }
    }

    override fun getItemCount(): Int = theaters.size
}
