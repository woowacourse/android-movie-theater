package woowacourse.movie.presentation.ui.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import woowacourse.movie.R
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.domain.model.ScreenView
import woowacourse.movie.domain.model.TheaterCount
import woowacourse.movie.domain.repository.DummyScreens
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.ui.detail.MovieDetailActivity
import woowacourse.movie.presentation.ui.main.home.adapter.ScreenRecyclerViewAdapter
import woowacourse.movie.presentation.ui.main.home.bottom.BottomTheatersFragment

class HomeFragment : Fragment(), HomeContract.View {
    val presenter: HomePresenter by lazy { HomePresenter(this, DummyScreens()) }
    private val adapter: ScreenRecyclerViewAdapter by lazy { ScreenRecyclerViewAdapter(presenter) }
    private lateinit var dialog: BottomTheatersFragment

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        binding.presenter = presenter
        initAdapter()
        return binding.root
    }

    private fun initAdapter() {
        binding.rvScreen.adapter = adapter
        presenter.fetchScreens()
    }

    override fun showScreenList(screens: List<ScreenView>) {
        adapter.submitList(screens)
    }

    override fun showBottomTheater(
        theaterCounts: List<TheaterCount>,
        movieId: Int,
    ) {
        dialog = BottomTheatersFragment(theaterCounts, presenter, movieId)
        dialog.show(this.parentFragmentManager, null)
    }

    override fun navigateToDetail(
        movieId: Int,
        theaterId: Int,
    ) {
        dialog.dismiss()
        MovieDetailActivity.startActivity(requireActivity(), movieId, theaterId)
    }

    override fun showToastMessage(messageType: MessageType) {
        Toast.makeText(requireActivity(), messageType.toMessage(), Toast.LENGTH_SHORT).show()
    }

    override fun showToastMessage(e: Throwable) {
        Toast.makeText(requireActivity(), e.toErrorMessage(), Toast.LENGTH_SHORT).show()
    }

    override fun showSnackBar(e: Throwable) {
        Snackbar.make(
            binding.root,
            e.toErrorMessage(),
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    override fun showSnackBar(messageType: MessageType) {
        Snackbar.make(
            binding.root,
            messageType.toMessage(),
            Snackbar.LENGTH_SHORT,
        ).show()
    }

    private fun MessageType.toMessage(): String {
        return when (this) {
            is MessageType.TicketMaxCountMessage ->
                getString(
                    R.string.ticke_max_count_message,
                    this.count,
                )

            is MessageType.TicketMinCountMessage ->
                getString(
                    R.string.ticke_min_count_message,
                    this.count,
                )

            is MessageType.AllSeatsSelectedMessage ->
                getString(
                    R.string.all_seats_selected_message,
                    this.count,
                )

            is MessageType.ReservationSuccessMessage -> getString(R.string.reservation_success_message)
        }
    }

    private fun Throwable.toErrorMessage(): String {
        return when (this) {
            is NoSuchElementException -> getString(R.string.no_such_element_exception_message)
            else -> getString(R.string.unforeseen_error_message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
