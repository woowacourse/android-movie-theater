package woowacourse.movie.presentation.homefragments.movieList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.presentation.homefragments.movieList.listener.TheaterClickListener
import woowacourse.movie.presentation.homefragments.movieList.uimodel.ScreeningTheater
import woowacourse.movie.presentation.homefragments.movieList.viewholder.TheaterViewHolder

class TheaterAdapter(
    private val screeningTheaters: List<ScreeningTheater>,
    private val listener: TheaterClickListener,
) : RecyclerView.Adapter<TheaterViewHolder>() {
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
        holder.bind(screeningTheaters[position], listener)
    }

    override fun getItemCount(): Int {
        return screeningTheaters.size
    }
}
