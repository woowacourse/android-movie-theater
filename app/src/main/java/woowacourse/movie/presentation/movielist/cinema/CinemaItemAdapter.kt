package woowacourse.movie.presentation.movielist.cinema

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.CinemaListItemBinding
import woowacourse.movie.presentation.model.CinemaModel

class CinemaItemAdapter(
    private val cinemaModels: List<CinemaModel>,
    private val clickBook: (CinemaModel) -> Unit,
) : RecyclerView.Adapter<CinemaItemViewHolder>() {

    private lateinit var cinemaListItemBinding: CinemaListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CinemaItemViewHolder {
        cinemaListItemBinding =
            CinemaListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CinemaItemViewHolder(cinemaListItemBinding, clickBook)
    }

    override fun onBindViewHolder(holder: CinemaItemViewHolder, position: Int) {
        val cinema = cinemaModels[position]
        holder.bind(cinema)
    }

    override fun getItemCount(): Int {
        val size = cinemaModels.size
        return size
    }
}
