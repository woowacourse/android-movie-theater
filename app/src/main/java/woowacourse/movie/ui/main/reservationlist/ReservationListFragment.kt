package woowacourse.movie.ui.main.reservationlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.adapter.ReservationAdapter
import woowacourse.movie.data.model.uimodel.ReservationUiModel
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.repository.ReservationListRepositoryImpl
import woowacourse.movie.ui.reservationresult.ReservationResultActivity

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

        setUpPresenter()
        setUpAdapter()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun updateReservations() {
        val reservations = presenter.getReservations()
        adapter.updateReservationItems(reservations, ::setOnReservationItemClick)
    }

    override fun setOnReservationItemClick(reservationUiModel: ReservationUiModel) {
        val intent = ReservationResultActivity.getIntent(requireContext(), reservationUiModel.movie, reservationUiModel.tickets)
        startActivity(intent)
    }
}
