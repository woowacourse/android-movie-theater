package woowacourse.movie.presentation.view.bottomNavigationBar

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.database.ReservationDatabase
import woowacourse.movie.databinding.FragmentReservationDetailsBinding
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservation.result.ReservationResultActivity
import woowacourse.movie.presentation.view.reservation.result.ReservationResultActivity.Companion.INTENT_TICKET
import woowacourse.movie.presentation.view.reservationdetails.ReservationDetailsContract
import woowacourse.movie.presentation.view.reservationdetails.ReservationDetailsPresenterImpl
import woowacourse.movie.presentation.view.reservationdetails.adapter.ReservationDetailsListAdapter

class ReservationDetailsFragment : Fragment(), ReservationDetailsContract.View, ReservationDetailsContract.ViewActions {
    private lateinit var adapter: ReservationDetailsListAdapter
    private lateinit var reservationDatabase: ReservationDatabase
    private lateinit var presenter: ReservationDetailsContract.Presenter
    private lateinit var binding: FragmentReservationDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        reservationDatabase = ReservationDatabase.getDatabase(requireActivity().applicationContext)
        adapter = ReservationDetailsListAdapter(emptyList(), this)
        presenter = ReservationDetailsPresenterImpl(this, reservationDatabase.reservationDao())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentReservationDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding.reservationDetailsList.layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration =
            DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        binding.reservationDetailsList.addItemDecoration(dividerItemDecoration)
        binding.reservationDetailsList.adapter = adapter
        presenter.loadDetailsList()
    }

    override fun showDetailsList(ticketList: List<MovieTicketUiModel>) {
        adapter.updateReservationDetailsList(ticketList)
    }

    override fun moveToReservationResult(ticketUiModel: MovieTicketUiModel) {
        val intent = Intent(requireContext(), ReservationResultActivity::class.java)
        intent.putExtra(INTENT_TICKET, ticketUiModel)
        startActivity(intent)
    }

    override fun onDetailItemClicked(ticketId: Long) {
        presenter.onDetailItemClicked(ticketId)
    }
}
