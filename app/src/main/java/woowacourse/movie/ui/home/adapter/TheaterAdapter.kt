package woowacourse.movie.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.databinding.ItemTheaterBinding
import woowacourse.movie.model.movie.Theater

class TheaterAdapter(
    private val theaters: List<Theater>,
) : RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TheaterViewHolder {
        val binding: ItemTheaterBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_theater,
            parent,
            false
        )

        return TheaterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TheaterViewHolder, position: Int) {
        holder.bind(theaters[position])
    }

    override fun getItemCount(): Int = theaters.size

    class TheaterViewHolder(val binding: ItemTheaterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val context = binding.root.context
        fun bind(theater: Theater) {
            binding.theaterTitleText.text = context.getString(R.string.theater, theater.name)
            // TODO 상영 횟수 구하는 로직 구현
            binding.screeningTimesCountText.text = context.getString(R.string.screening_times_count, 10)
        }

//        private fun generateScreeningTimeCount()
    }

}
