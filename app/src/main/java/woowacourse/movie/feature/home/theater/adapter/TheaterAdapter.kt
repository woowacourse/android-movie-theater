package woowacourse.movie.feature.home.theater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.Theater

typealias TheaterItemClickListener = (theaterPosition: Int) -> Unit

class TheaterAdapter(
    private val theaters: List<Theater>,
    private val onTheaterItemClick: TheaterItemClickListener,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTheaterBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return theaters.size
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(theaters[position], onTheaterItemClick)
    }
}
