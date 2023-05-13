package woowacourse.movie.theater.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItmeBinding
import woowacourse.movie.theater.dto.MovieTheaterDto

class TheaterAdapter(
    private val theaters: List<MovieTheaterDto>,
    private val onClick: (MovieTheaterDto) -> Unit
) : RecyclerView.Adapter<TheaterViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheaterViewHolder {
        val binding = TheaterItmeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding).apply {
            binding.rightArrow.setOnClickListener {
                onClick(theaters[bindingAdapterPosition])
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
