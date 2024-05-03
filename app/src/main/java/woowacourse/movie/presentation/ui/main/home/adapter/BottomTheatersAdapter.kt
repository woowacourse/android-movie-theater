package woowacourse.movie.presentation.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.HolderTheaterBinding
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.presentation.ui.main.home.bottom.BottomTheaterActionHandler

class BottomTheatersAdapter(
    private val actionHandler: BottomTheaterActionHandler,
    private val theaterCounts: List<TheaterCount> = mutableListOf(),
    private val movieId: Int,
) : RecyclerView.Adapter<BottomTheatersViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BottomTheatersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = HolderTheaterBinding.inflate(inflater, parent, false)
        return BottomTheatersViewHolder(binding, actionHandler, movieId)
    }

    override fun getItemCount(): Int = theaterCounts.size

    override fun onBindViewHolder(
        holder: BottomTheatersViewHolder,
        position: Int,
    ) {
        holder.bind(theaterCounts[position])
    }
}
