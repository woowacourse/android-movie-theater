package woowacourse.movie.presentation.ui.screen.bottom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.domain.model.TheaterCount

class BottomTheatersAdapter(
    private val actionHandler: BottomTheaterActionHandler,
    private val theaterCounts: List<TheaterCount> = mutableListOf(),
    val movieId: Int,
) : RecyclerView.Adapter<BottomTheatersViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BottomTheatersViewHolder {
        return BottomTheatersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.holder_theater, parent, false),
            actionHandler,
            movieId,
        )
    }

    override fun getItemCount(): Int = theaterCounts.size

    override fun onBindViewHolder(
        holder: BottomTheatersViewHolder,
        position: Int,
    ) {
        holder.bind(theaterCounts[position])
    }
}
