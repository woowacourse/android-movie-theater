package woowacourse.movie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import woowacourse.movie.databinding.BottomSheetTheatersBinding
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.model.main.MainData
import woowacourse.movie.model.main.MainModelHandler
import woowacourse.movie.ui.booking.BookingActivity
import woowacourse.movie.ui.main.adapter.recyclerview.MainAdapter

class HomeFragment : Fragment(), HomeContract.View {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val homePresenter: HomePresenter by lazy {
        HomePresenter(
            view = this,
            repository = MainModelHandler
        )
    }
    private val theaterSheetBinding: BottomSheetTheatersBinding by lazy {
        BottomSheetTheatersBinding.inflate(layoutInflater)
    }
    private val bottomSheetDialog: BottomSheetDialog by lazy {
        BottomSheetDialog(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        homePresenter.initMainData()
    }

    override fun initAdapter(mainData: List<MainData>) {
        val mainAdapter = MainAdapter(
            context = requireContext(),
            onBooked = ::onBooked,
            onAdClicked = ::onAdClicked,
        )

        binding.listMainMovie.adapter = mainAdapter
        mainAdapter.initMovies(mainData)
    }

    private fun onBooked(movieId: Long) {
        theaterSheetBinding.recyclerTheaters.adapter = TheaterRecyclerAdapter(
            theaters = homePresenter.theaters,
            movieId = movieId,
            onTheaterSelected = ::onTheaterSelected
        )
        bottomSheetDialog.setContentView(theaterSheetBinding.root)
        bottomSheetDialog.show()
    }

    private fun onTheaterSelected(
        movieId: Long,
        theaterId: Long
    ) {
        startActivity(
            BookingActivity.getIntent(
                context = requireContext(),
                movieId = movieId,
                theaterId = theaterId
            )
        )
    }

    private fun onAdClicked(intent: Intent) {
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}
