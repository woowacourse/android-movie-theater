package woowacourse.movie.presentation.main.home.theater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.domain.model.home.Theater

class TheaterAdapter(
    private val movieId: Long,
    private val theaters: List<Theater>,
    private val onTheaterItemClickListener: OnTheaterItemClickListener
) : RecyclerView.Adapter<TheaterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val binding = TheaterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        val theater = theaters[position]
        holder.bind(movieId, theater, onTheaterItemClickListener)
    }

    override fun getItemCount(): Int = theaters.size
}
