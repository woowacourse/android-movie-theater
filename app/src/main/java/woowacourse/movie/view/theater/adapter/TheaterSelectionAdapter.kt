package woowacourse.movie.view.theater.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.Theater

typealias OnSelectTheater = (theaterId: Int) -> Unit

class TheaterSelectionAdapter(
    private val onSelectTheater: OnSelectTheater,
) : RecyclerView.Adapter<TheaterSelectionViewHolder>() {
    private var theaters: List<Theater> = emptyList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterSelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemTheaterBinding.inflate(inflater, parent, false)

        return TheaterSelectionViewHolder(view, onSelectTheater)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(
        holder: TheaterSelectionViewHolder,
        position: Int,
    ) {
        holder.bind(theaters[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateTheaters(theaters: List<Theater>)  {
        this.theaters = theaters
        notifyDataSetChanged()
    }
}
