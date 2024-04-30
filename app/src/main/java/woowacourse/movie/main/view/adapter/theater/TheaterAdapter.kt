package woowacourse.movie.main.view.adapter.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.Theater

class TheaterAdapter(
    private val theaters: List<Theater>,
    private val onTheaterItemClick: (Int) -> Unit,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val theaterItemBinding = ItemTheaterBinding.inflate(inflater, parent, false)
        return TheaterViewHolder(theaterItemBinding)
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
