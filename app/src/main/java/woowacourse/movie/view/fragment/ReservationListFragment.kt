package woowacourse.movie.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import woowacourse.movie.R
import woowacourse.movie.data.database.MovieDao
import woowacourse.movie.databinding.FragmentReservationListBinding
import woowacourse.movie.view.activity.ReservationResultActivity
import woowacourse.movie.view.adapter.ReservationAdapter

class ReservationListFragment : Fragment() {
    private lateinit var binding: FragmentReservationListBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reservation_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        makeReservationRecyclerView()
    }

    private fun makeReservationRecyclerView() {
        binding.reservationListRecycler.adapter = ReservationAdapter(MovieDao(requireContext())) {
            startActivity(ReservationResultActivity.from(binding.root.context, it))
        }.also { it.presenter.setReservation() }
        val decoration = DividerItemDecoration(binding.root.context, DividerItemDecoration.VERTICAL)
        binding.reservationListRecycler.addItemDecoration(decoration)
    }
}
