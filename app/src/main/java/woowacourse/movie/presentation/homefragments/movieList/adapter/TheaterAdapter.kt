package woowacourse.movie.presentation.homefragments.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.Theater
import woowacourse.movie.presentation.homefragments.movieList.listener.TheaterClickListener
import woowacourse.movie.presentation.homefragments.movieList.viewholder.TheaterViewHolder

class TheaterAdapter(
    private val listener: TheaterClickListener,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    private lateinit var theaters: List<Theater>
    private var movieId: Long = 0L

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        val binding = ItemTheaterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(theaters[position], movieId, listener)
    }

    override fun getItemCount(): Int {
        return theaters.size
    }

    fun updateMovieIdAndTheaters(
        newMovieId: Long,
        newTheaters: List<Theater>,
    ) {
        movieId = newMovieId
        theaters = newTheaters
        notifyDataSetChanged()
    }
}
