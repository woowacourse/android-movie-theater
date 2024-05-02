package woowacourse.movie.ui.detail.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.HolderScreenDetailTicketBinding

class ScreenDetailTicketView(
    context: Context,
    attrs: AttributeSet? = null,
) : TicketView, ConstraintLayout(context, attrs) {
    private val binding: HolderScreenDetailTicketBinding =
        DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.holder_screen_detail_ticket, this, true)

    override fun initClickListener(
        screenId: Int,
        ticketReserveListener: TicketReserveListener<Int>,
    ) {
        binding.btnPlus.setOnClickListener {
            ticketReserveListener.increaseTicket()
        }
        binding.btnMinus.setOnClickListener {
            ticketReserveListener.decreaseTicket()
        }
        binding.btnReserveDone.setOnClickListener {
            ticketReserveListener.reserve(screenId = screenId)
        }
    }

    override fun updateTicketCount(count: Int) {
        binding.ticketCount = count
    }

    override fun ticketCount(): Int = binding.ticketCount

    override fun restoreTicketCount(count: Int) {
        binding.ticketCount = count
    }
}
