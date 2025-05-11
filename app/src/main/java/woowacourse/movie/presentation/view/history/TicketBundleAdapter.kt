package woowacourse.movie.presentation.view.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemHistoryBinding
import woowacourse.movie.presentation.model.TicketBundleUiModel

class TicketBundleAdapter : ListAdapter<TicketBundleUiModel, TicketBundleAdapter.TicketViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TicketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return TicketViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class TicketViewHolder(
        private val binding: ItemHistoryBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TicketBundleUiModel) {
            binding.ticketBundle = item
            binding.executePendingBindings()
        }
    }

    companion object {
        private val DIFF_CALLBACK =
            object : DiffUtil.ItemCallback<TicketBundleUiModel>() {
                override fun areItemsTheSame(
                    oldItem: TicketBundleUiModel,
                    newItem: TicketBundleUiModel,
                ): Boolean = oldItem.dateTime == newItem.dateTime

                override fun areContentsTheSame(
                    oldItem: TicketBundleUiModel,
                    newItem: TicketBundleUiModel,
                ): Boolean = oldItem == newItem
            }
    }
}
