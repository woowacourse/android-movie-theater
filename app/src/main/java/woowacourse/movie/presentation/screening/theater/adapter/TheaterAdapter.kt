package woowacourse.movie.presentation.screening.theater.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.TheaterItemBinding
import woowacourse.movie.presentation.screening.theater.TheaterBottomSheetContract
import woowacourse.movie.presentation.uimodel.TheaterUiModel

class TheaterAdapter(
    private val theaters: List<TheaterUiModel>,
    private val clickListener: TheaterBottomSheetContract.ItemListener,
) : RecyclerView.Adapter<TheaterViewHolder>() {
    private lateinit var binding: TheaterItemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TheaterViewHolder {
        binding = TheaterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TheaterViewHolder(binding)
    }

    override fun getItemCount(): Int = theaters.size

    override fun onBindViewHolder(
        holder: TheaterViewHolder,
        position: Int,
    ) {
        holder.bind(theaters.get(position), clickListener)
    }
}
