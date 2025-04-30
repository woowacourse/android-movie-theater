package woowacourse.movie.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.Screenings

class TheaterAdapter(
    private val items: Screenings,
    private val onBookingClick: (Screening) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_theater, parent, false)
        return TheaterViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
    ) {
        val item = items.value[position]
        (holder as TheaterViewHolder).bind(item, onBookingClick)
    }

    override fun getItemCount(): Int = items.value.size
}
