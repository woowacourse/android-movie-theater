package woowacourse.movie.ui.home.bottomsheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.uimodel.TheaterModel

class TheaterListAdapter(
    private val modelItems: List<TheaterModel>,
    private val onItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<TheaterListAdapter.TheaterListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_theater,
            parent,
            false,
        )

        return TheaterListViewHolder(ItemTheaterBinding.bind(view), onItemClick)
    }

    override fun getItemCount(): Int = modelItems.size

    override fun onBindViewHolder(holder: TheaterListViewHolder, position: Int) {
        holder.onBind(modelItems[position])
    }

    class TheaterListViewHolder(
        private val binding: ItemTheaterBinding,
        onClick: (Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener { onClick(adapterPosition) }
            binding.itemTheaterButton.setOnClickListener { onClick(adapterPosition) }
        }

        fun onBind(theaterModel: TheaterModel) {
            binding.theater = theaterModel
        }
    }
}
