package woowacourse.movie.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.domain.model.ScreeningInfo

//import woowacourse.movie.domain.model.Theater

class TheaterAdapter(
    private val items: List<ScreeningInfo>,
    private val onClick: (ScreeningInfo) -> Unit,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    private lateinit var binding: ItemTheaterBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_theater, parent, false)
        return TheaterViewHolder(parent.context, binding, onClick)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(items[position])
    }

    class TheaterViewHolder(
        private val context: Context,
        private val binding: ItemTheaterBinding,
        private val onClick: (ScreeningInfo) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ScreeningInfo) {
            binding.textviewTheaterName.text = item.theater
            binding.textviewScreeningTime.text = context.getString(R.string.theater_text, item.times.size)
            binding.constraintlayoutTheater.setOnClickListener {
                onClick(item)
            }
        }
    }
}
