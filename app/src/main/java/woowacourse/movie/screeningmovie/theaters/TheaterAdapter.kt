package woowacourse.movie.screeningmovie.theaters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.screeningmovie.AdapterClickListener

class TheaterAdapter(
    private val items: List<TheaterUiModel>,
    private val clickListener: AdapterClickListener,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    class TheaterViewHolder(private val binding: ItemTheaterBinding, private val clickListener: AdapterClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(item: TheaterUiModel) {
            binding.theater = item
            binding.clickListener = clickListener
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val binding = ItemTheaterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding, clickListener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.onBind(items[position])
    }
}
