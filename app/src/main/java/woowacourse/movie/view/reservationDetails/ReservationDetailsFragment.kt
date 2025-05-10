package woowacourse.movie.view.reservationDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentReservationDetailsBinding
import woowacourse.movie.model.reservation.ReservationDatabase

class ReservationDetailsFragment : Fragment() {
    private lateinit var binding: FragmentReservationDetailsBinding
    private lateinit var reservationDetailAdapter: ReservationDetailAdapter
    private val db by lazy { ReservationDatabase.getDatabase(requireContext()) }
    private val dao by lazy { db.reservationDao() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_reservation_details,
                container,
                false,
            )
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)

        reservationDetailAdapter = ReservationDetailAdapter()
        binding.rvReservationDetails.layoutManager = LinearLayoutManager(requireContext())
        binding.rvReservationDetails.adapter = reservationDetailAdapter

        Thread {
            val reservations = dao.findReservation()

            requireActivity().runOnUiThread {
                reservationDetailAdapter.submitList(reservations)
            }
        }.start()
    }
}
