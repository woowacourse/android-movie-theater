package woowacourse.movie.ui.home

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.model.TheaterUiModel

class TheaterRecyclerAdapter(
    private val theaters: List<TheaterUiModel>,
    private val movieId: Long,
    private val onTheaterSelected: (movieId: Long, theaterId: Long) -> Unit,
) : RecyclerView.Adapter<TheaterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {

        return TheaterViewHolder.from(parent)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        holder.bind(
            theater = theaters[position],
            onTheaterSelected = { onTheaterSelected(movieId, theaters[position].id) }
        )
    }
}
