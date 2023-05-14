package woowacourse.movie.ui.fragment.reservationlist.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import woowacourse.movie.data.db.DBHelper
import woowacourse.movie.data.reservation.ReservationLocalDataSource
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.ui.activity.MovieTicketActivity
import woowacourse.movie.ui.fragment.reservationlist.ReservationListContract
import woowacourse.movie.ui.fragment.reservationlist.adapter.ReservationAdapter
import woowacourse.movie.ui.fragment.reservationlist.presenter.ReservationListPresenter
import woowacourse.movie.ui.model.MovieTicketModel

class ReservationListFragment : Fragment(), ReservationListContract.View {
    private lateinit var binding: FragmentReservationListBinding
    override lateinit var presenter: ReservationListContract.Presenter
    private lateinit var reservationAdapter: ReservationAdapter

    private var itemsCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPresenter()

        setFragmentResultListener(REQUEST_KEY_ITEM_INSERTION) { _, _ ->
            presenter.checkItemInsertion(itemsCount)
        }

        presenter.setUpReservations()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReservationListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.setReservations()
    }

    override fun setReservations(reservations: List<MovieTicketModel>) {
        binding.rvReservation.addItemDecoration(
            DividerItemDecoration(
                binding.rvReservation.context,
                LinearLayoutManager.VERTICAL
            )
        )
        reservationAdapter = ReservationAdapter(reservations) { moveToTicketActivity(it) }
        itemsCount = reservationAdapter.itemCount
        binding.rvReservation.adapter = reservationAdapter
    }

    override fun notifyReservationInsertion(count: Int) {
        reservationAdapter.notifyItemRangeInserted(itemsCount, count)
        itemsCount += count
    }

    private fun initPresenter() {
        val db = DBHelper(requireContext()).readableDatabase
        val dataSource = ReservationLocalDataSource(db)
        val repository = ReservationRepository(dataSource)
        presenter = ReservationListPresenter(repository, this)
    }

    private fun moveToTicketActivity(ticketModel: MovieTicketModel) {
        val intent = MovieTicketActivity.createIntent(requireActivity(), ticketModel)
        startActivity(intent)
    }

    companion object {
        const val REQUEST_KEY_ITEM_INSERTION = "item_insertion"
    }
}
