package woowacourse.movie.presentation.view.reservationdetails.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.databinding.ItemReservationDetailBinding
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservationdetails.ReservationDetailsContract

class ReservationDetailsListAdapter(
    private var reservationDetailsList: List<MovieTicketUiModel>,
    private val onItemClickListener: ReservationDetailsContract.ViewActions,
) : RecyclerView.Adapter<DetailViewHolder>() {
    private lateinit var binding: ItemReservationDetailBinding

    fun updateReservationDetailsList(newReservationDetailsList: List<MovieTicketUiModel>) {
        reservationDetailsList = newReservationDetailsList
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): DetailViewHolder {
        binding =
            ItemReservationDetailBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false,
                )
        return DetailViewHolder(binding)
    }

    override fun getItemCount(): Int = reservationDetailsList.size

    override fun onBindViewHolder(
        holder: DetailViewHolder,
        position: Int,
    ) {
        holder.bind(reservationDetailsList[position], onItemClickListener)
    }
}
