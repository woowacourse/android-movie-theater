package woowacourse.movie.presentation.movieList.cinemaListDialog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import woowacourse.movie.databinding.ItemBottomSheetTheatersBinding
import woowacourse.movie.model.Cinema

class ChooseCinemasAdapter(private val onTheaterClicked: (Cinema) -> Unit) :
    ListAdapter<Cinema, ChooseCinemasViewHolder>(theaterComparator) {
    private lateinit var inflater: LayoutInflater

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ChooseCinemasViewHolder {
        if (::inflater.isInitialized.not()) inflater = LayoutInflater.from(parent.context)
        val binding = ItemBottomSheetTheatersBinding.inflate(inflater, parent, false)

        return ChooseCinemasViewHolder(binding, onTheaterClicked)
    }

    override fun onBindViewHolder(
        holder: ChooseCinemasViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    companion object {
        private val theaterComparator =
            object : DiffUtil.ItemCallback<Cinema>() {
                override fun areItemsTheSame(
                    oldItem: Cinema,
                    newItem: Cinema,
                ): Boolean {
                    return oldItem.cinemaName == newItem.cinemaName
                }

                override fun areContentsTheSame(
                    oldItem: Cinema,
                    newItem: Cinema,
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
