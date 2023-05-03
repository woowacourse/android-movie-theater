package woowacourse.app.ui.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.app.ui.booking.BookingActivity
import woowacourse.app.ui.main.home.adapter.recyclerview.HomeAdapter
import woowacourse.app.usecase.main.MainUseCase
import woowacourse.data.advertisement.AdvertisementRepositoryImpl
import woowacourse.data.movie.MovieRepositoryImpl
import woowacourse.movie.R

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter(view)
    }

    private fun initAdapter(view: View) {
        val homeAdapter = HomeAdapter(
            requireContext(),
            { clickBook(it) },
            { clickAdvertisement(it) },
        )
        view.findViewById<RecyclerView>(R.id.listMainMovie).adapter = homeAdapter
        homeAdapter.initMovies(
            MainUseCase(
                AdvertisementRepositoryImpl(),
                MovieRepositoryImpl(),
            ).getMainData(),
        )
    }

    private fun clickBook(movieId: Long) {
        startActivity(BookingActivity.getIntent(requireContext(), movieId))
    }

    private fun clickAdvertisement(intent: Intent) {
        startActivity(intent)
    }
}
