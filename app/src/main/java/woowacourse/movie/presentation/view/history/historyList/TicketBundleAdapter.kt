package woowacourse.movie.presentation.view.history.historyList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemHistoryBinding
import woowacourse.movie.presentation.model.TicketBundleUiModel
class TicketBundleAdapter(
    private val onClick: (TicketBundleUiModel) -> Unit,
) : ListAdapter<TicketBundleUiModel, TicketBundleAdapter.TicketViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): TicketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemHistoryBinding.inflate(inflater, parent, false)
        return TicketViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(
        holder: TicketViewHolder,
        position: Int,
    ) {
        holder.bind(getItem(position))
    }

    class TicketViewHolder(
        private val binding: ItemHistoryBinding,
        private val onClick: (TicketBundleUiModel) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        private var currentItem: TicketBundleUiModel? = null

        init {
            binding.root.setOnClickListener {
                currentItem?.let { onClick(it) }
            }
        }

        fun bind(item: TicketBundleUiModel) {
            currentItem = item
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
                ) = oldItem.dateTime == newItem.dateTime

                override fun areContentsTheSame(
                    oldItem: TicketBundleUiModel,
                    newItem: TicketBundleUiModel,
                ) = oldItem == newItem
            }
    }
}