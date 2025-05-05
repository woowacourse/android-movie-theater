package woowacourse.movie.feature.theaters.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.feature.model.ScreeningUiModel

class TheaterAdapter(
    private val items: List<ScreeningUiModel>,
    private val onBookingClick: (ScreeningUiModel) -> Unit,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTheaterBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(binding)
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
