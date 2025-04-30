package woowacourse.movie.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.domain.model.ScreeningInfo

class TheaterAdapter(
    private val items: List<ScreeningInfo>,
    private val onClickTheater: (ScreeningInfo) -> Unit,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val binding =
            DataBindingUtil.inflate<ItemTheaterBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_theater,
                parent,
                false,
            )
        return TheaterViewHolder(binding, onClickTheater)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    class TheaterViewHolder(
        private val binding: ItemTheaterBinding,
        private val onClickTheater: (ScreeningInfo) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ScreeningInfo) {
            binding.screeningInfo = item
            binding.handler = ClickListener<ScreeningInfo> { onClickTheater(it) }
        }
    }
}
