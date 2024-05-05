package woowacourse.movie.feature.theater.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.theater.Theater

typealias OnTheaterSelected = (theaterId: Int) -> Unit

class TheaterSelectionAdapter(
    private val onTheaterSelected: OnTheaterSelected,
) : RecyclerView.Adapter<TheaterSelectionViewHolder>() {
    private var theaters = listOf<Theater>()
    private var screeningCounts = listOf<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterSelectionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ItemTheaterBinding.inflate(inflater, parent, false)
        return TheaterSelectionViewHolder(view)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(
        holder: TheaterSelectionViewHolder,
        position: Int,
    ) {
        holder.bind(theaters[position], screeningCounts[position], onTheaterSelected)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(
        newTheaters: List<Theater>,
        nuwScreeningCounts: List<Int>,
    ) {
        theaters = newTheaters
        screeningCounts = nuwScreeningCounts
        notifyDataSetChanged()
    }
}
