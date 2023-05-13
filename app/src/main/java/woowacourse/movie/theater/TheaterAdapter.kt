package woowacourse.movie.theater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItmeBinding
import woowacourse.movie.dto.theater.MovieTheaterDto
import woowacourse.movie.movielist.OnClickListener

class TheaterAdapter(private val theaters: List<MovieTheaterDto>) : RecyclerView.Adapter<TheaterViewHolder>() {
    lateinit var itemViewClick: OnClickListener<MovieTheaterDto>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val binding = TheaterItmeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding).apply {
            binding.rightArrow.setOnClickListener {
                itemViewClick.onClick(theaters[bindingAdapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        val item = theaters[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return theaters.size
    }
}
