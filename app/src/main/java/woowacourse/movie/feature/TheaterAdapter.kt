package woowacourse.movie.feature

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.feature.model.ScreeningUiModel

class TheaterAdapter(
    private val items: List<ScreeningUiModel>,
    private val onBookingClick: (ScreeningUiModel) -> Unit,
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
        val item: ScreeningUiModel = items[position]
        (holder as TheaterViewHolder).bind(item) { onBookingClick(item) }
    }

    override fun getItemCount(): Int = items.size
}
