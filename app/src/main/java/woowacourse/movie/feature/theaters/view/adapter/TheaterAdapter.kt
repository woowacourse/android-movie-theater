package woowacourse.movie.feature.theaters.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.feature.model.ScreeningUiModel

class TheaterAdapter(
    private val items: List<ScreeningUiModel>,
    private val handler: TheaterViewHolder.Handler,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val binding = ItemTheaterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding, handler)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val item: ScreeningUiModel = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = items.size
}
