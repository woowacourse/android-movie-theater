package woowacourse.movie.movie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import woowacourse.movie.R
import woowacourse.movie.databinding.BottomSheetItemBinding
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.Theater

class TheaterListAdapter(
    private val items: List<Theater>,
    val movie: Movie,
    val onClicked: (Theater) -> Unit,
) : RecyclerView.Adapter<TheaterListAdapter.TheaterViewHolder>() {
    private lateinit var binding: BottomSheetItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.bottom_sheet_item, parent, false)
        return TheaterViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        val item = items[position]
        holder.setItem(item)
    }

    inner class TheaterViewHolder(binding: BottomSheetItemBinding) : ViewHolder(binding.root) {
        fun setItem(item: Theater) {
            binding.theater = item
            binding.movie = movie
            itemView.setOnClickListener {
                onClicked(item)
            }
        }
    }
}
