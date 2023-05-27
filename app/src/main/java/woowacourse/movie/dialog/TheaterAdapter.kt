package woowacourse.movie.dialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.RecyclerviewTheaterItemBinding
import woowacourse.movie.domain.model.TheaterMovie

class TheaterAdapter(
    private val theaterMovies: List<TheaterMovie>,
    val onClickItem: (data: TheaterMovie) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = RecyclerviewTheaterItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TheaterViewHolder(binding) { onClickItem(theaterMovies[it]) }
    }

    override fun getItemCount(): Int {
        return theaterMovies.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as TheaterViewHolder).bind(theaterMovies[position])
    }
}
