package woowacourse.movie.view.home

import android.Manifest.permission
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import woowacourse.movie.R
import woowacourse.movie.data.storage.DefaultNotificationPermissionStorage
import woowacourse.movie.databinding.FragmentHomeBinding
import woowacourse.movie.model.theater.TheaterMovieSchedules
import woowacourse.movie.presenter.home.HomeContracts
import woowacourse.movie.presenter.home.HomePresenter
import woowacourse.movie.view.extension.showShortToast
import woowacourse.movie.view.home.theater.TheaterBottomSheetDialogFragment

class HomeFragment :
    Fragment(R.layout.fragment_home),
    HomeContracts.View {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val presenter by lazy {
        HomePresenter(
            this,
            DefaultNotificationPermissionStorage(requireContext()),
        )
    }

    private val movieAdapter =
        MovieAdapter(
            movieClickListener =
                object : MovieClickListener {
                    override fun onReservationClick(movieId: Long) {
                        presenter.updateTheater(movieId)
                    }

                    override fun onAdvertisementClick(url: String) {
                        presenter.updateAdvertisement(url)
                    }
                },
        )

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestPermission(),
        ) { isGranted: Boolean ->
            presenter.updateNotificationPermission(isGranted)
        }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)
        presenter.updateView()
        requestNotificationPermission()
    }

    private fun requestNotificationPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (shouldShowRequestPermissionRationale(permission.POST_NOTIFICATIONS)) {
                    requireContext().showShortToast(getString(R.string.alarm_permission_denied_message))
                } else {
                    requestPermissionLauncher.launch(permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

    override fun showMovies(movies: List<MovieType>) {
        binding.rvMainMovies.adapter = movieAdapter
        movieAdapter.submitList(movies)
    }

    override fun showTheaters(theaterMovieSchedules: TheaterMovieSchedules) {
        TheaterBottomSheetDialogFragment
            .newInstance(theaterMovieSchedules)
            .show(parentFragmentManager, "jay")
    }

    override fun showAdvertisement(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, url.toUri())
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
