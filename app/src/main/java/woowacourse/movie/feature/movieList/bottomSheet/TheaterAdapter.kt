package woowacourse.movie.feature.movieList.bottomSheet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterLayoutBinding

class TheaterAdapter(
    items: List<TheaterItemModel> = listOf(),
) : RecyclerView.Adapter<TheaterViewHolder>() {

    private var _items: List<TheaterItemModel> = items.toList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheaterViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTheaterLayoutBinding.inflate(
            layoutInflater,
            parent,
            false
        )

        return TheaterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int
    ) {
        holder.bind(_items[position])
    }

    override fun getItemCount(): Int = _items.size
}
