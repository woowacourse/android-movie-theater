package woowacourse.movie.fragment.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.adapter.ReservationAdapter
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.repository.ReservationListRepositoryImpl
import woowacourse.movie.view.model.ReservationUiModel

class ReservationListFragment : Fragment(), ReservationListContract.View {
    override lateinit var presenter: ReservationListContract.Presenter
    private lateinit var adapter: ReservationAdapter
    private var _binding: FragmentReservationListBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentReservationListBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpPresenter()
        setUpAdapter()
        setUpOnClick()
    }

    private fun setUpPresenter() {
        presenter = ReservationListPresenter(this, ReservationListRepositoryImpl)
    }

    private fun setUpAdapter() {
        adapter = ReservationAdapter()
        binding.reservationRecyclerView.adapter = adapter
    }

    private fun setUpOnClick() {
        presenter.setUpClickListener()
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    override fun setOnReservationItemClick(reservationUiModel: ReservationUiModel) {
        val intent = ReservationResultActivity.generateIntent(requireContext(), reservationUiModel.movie, reservationUiModel.tickets)
        startActivity(intent)
    }

    override fun updateReservations() {
        val reservations = presenter.getReservations()
        adapter.updateReservationItems(reservations, ::setOnReservationItemClick)
    }
}
