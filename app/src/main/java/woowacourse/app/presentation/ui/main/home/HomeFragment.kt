package woowacourse.app.presentation.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.data.advertisement.AdvertisementDao
import woowacourse.app.data.advertisement.AdvertisementRepositoryImpl
import woowacourse.app.data.movie.MovieDao
import woowacourse.app.data.movie.MovieRepositoryImpl
import woowacourse.app.presentation.model.HomeData
import woowacourse.app.presentation.model.movie.MovieUiModel
import woowacourse.app.presentation.ui.booking.BookingActivity
import woowacourse.app.presentation.ui.main.home.adapter.recyclerview.HomeAdapter
import woowacourse.app.presentation.usecase.main.MainUseCase
import woowacourse.movie.R

class HomeFragment : Fragment(), HomeContract.View {
    override val presenter: HomeContract.Presenter by lazy {
        HomePresenter(
            MainUseCase(
                AdvertisementRepositoryImpl(AdvertisementDao(requireContext())),
                MovieRepositoryImpl(MovieDao(requireContext())),
            ),
            this,
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.fetchHomeData()
    }

    override fun initMovies(homeData: List<HomeData>) {
        val homeAdapter = HomeAdapter(
            requireContext(),
            { clickBook(it) },
            { clickAdvertisement(it) },
        )
        requireActivity().findViewById<RecyclerView>(R.id.listMainMovie).adapter = homeAdapter
        homeAdapter.initMovies(homeData)
    }

    private fun clickBook(movie: MovieUiModel) {
        startActivity(BookingActivity.getIntent(requireContext(), movie))
    }

    private fun clickAdvertisement(intent: Intent) {
        startActivity(intent)
    }
}
