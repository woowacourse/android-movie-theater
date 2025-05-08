package woowacourse.movie.presentation.view.reservationlist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import woowacourse.movie.data.ReservationDatabase
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.presentation.Extras
import woowacourse.movie.presentation.model.ReservationInfoUiModel
import woowacourse.movie.presentation.view.reservation.complete.ReservationCompleteActivity
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@BindingAdapter("reservationDateTime", "reservationTheaterName")
fun setReservationInfo(
    view: TextView,
    dateTime: LocalDateTime,
    theaterName: String,
) {
    val formatter = DateTimeFormatter.ofPattern("yyyy.M.d | HH:mm")
    view.text = "${dateTime.format(formatter)} | $theaterName 극장"
}

class ReservationListFragment :
    Fragment(),
    ReservationListContract.View {
    private lateinit var binding: FragmentReservationListBinding
    private val reservationDao by lazy {
        ReservationDatabase.getInstance(requireContext()).reservationDao()
    }
    private val presenter: ReservationListContract.Presenter by lazy {
        ReservationListPresenter(this, reservationDao)
    }
    private val reservationAdapter: ReservationAdapter by lazy {
        ReservationAdapter(
            object :
                ReservationClickListener {
                override fun onReservationClick(reservation: ReservationInfoUiModel) {
                    reservationSelect(reservation)
                }
            },
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReservationListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        setupReservationAdapter()
        presenter.fetchMovies()
    }

    override fun showReservations(reservations: List<ReservationInfoUiModel>) {
        reservationAdapter.submitList(reservations)
    }

    override fun navigateToComplete(reservation: ReservationInfoUiModel) {
        val intent =
            Intent(requireContext(), ReservationCompleteActivity::class.java).apply {
                putExtra(Extras.ReservationInfoData.RESERVATION_KEY, reservation)
            }
        startActivity(intent)
    }

    private fun setupReservationAdapter() {
        binding.rvReservationList.adapter = reservationAdapter
    }

    private fun reservationSelect(reservation: ReservationInfoUiModel) {
        presenter.reservationSelected(reservation)
    }
}
