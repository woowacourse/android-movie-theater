package woowacourse.movie.reservation

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.MovieApplication
import woowacourse.movie.data.Reservation
import woowacourse.movie.databinding.FragmentReservationBinding
import woowacourse.movie.moviebooked.MovieBookedActivity
import kotlin.concurrent.thread

class ReservationFragment : Fragment() {
    private var _binding: FragmentReservationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reservation, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        thread {
            val db = (context?.applicationContext as MovieApplication).database
            val reservations = db.reservationDao().getAll()
            Handler(Looper.getMainLooper()).post {
                showReservation(reservations)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showReservation(reservations: List<Reservation>) {
        binding.recyclerviewReservation.adapter =
            ReservationListAdapter(
                reservations,
                object : ReservationClickListener {
                    override fun clickReservation(reservation: Reservation) {
                        navigateToReservation(reservation.uid)
                    }
                },
            )
    }

    private fun navigateToReservation(id: Long) {
        val intent = MovieBookedActivity.newIntent(requireContext(), id)
        requireContext().startActivity(intent)
    }
}
