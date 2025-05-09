package woowacourse.movie.view.movies.cinema

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.domain.model.Screening

class CinemaSelectionAdapter(
    val items: List<Screening>,
    val eventListener: OnCinemaSelectionListener,
) : RecyclerView.Adapter<CinemaViewHolder>() {
    override fun onBindViewHolder(
        holder: CinemaViewHolder,
        position: Int,
    ) {
        holder.bind(items[position], eventListener)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): CinemaViewHolder {
        return CinemaViewHolder(parent)
    }

    override fun getItemCount(): Int = items.size
}
