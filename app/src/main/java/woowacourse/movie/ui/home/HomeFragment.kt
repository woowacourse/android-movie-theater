package woowacourse.movie.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.model.main.MainData
import woowacourse.movie.ui.booking.BookingActivity
import woowacourse.movie.ui.main.adapter.recyclerview.MainAdapter

class HomeFragment : Fragment(), HomeContract.View {

    private lateinit var binding: FragmentHomeBinding
    private val homePresenter: HomePresenter by lazy {
        HomePresenter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)

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
        startActivity(BookingActivity.getIntent(requireContext(), movieId))
    }

    private fun onAdClicked(intent: Intent) {
        startActivity(intent)
    }
}
