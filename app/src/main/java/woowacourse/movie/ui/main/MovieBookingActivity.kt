package woowacourse.movie.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieBookingBinding
import woowacourse.movie.ui.history.view.BookingHistoryFragment
import woowacourse.movie.ui.movielist.view.MovieListFragment
import woowacourse.movie.ui.settings.view.SettingsFragment
import woowacourse.movie.utils.Destination
import woowacourse.movie.utils.intentSerializable

class MovieBookingActivity : AppCompatActivity(), MovieBookingContract.View {
    private lateinit var binding: ActivityMovieBookingBinding
    private val presenter: MovieBookingContract.Presenter by lazy { MovieBookingPresenter(this) }
    private val movieListFragment by lazy { MovieListFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_booking)
        applyWindowInsets()

        setBottomNavigationView()

        if (savedInstanceState == null) {
            presenter.handleDestination(restoreDestination())
        } else {
            updateBottomNavigation()
        }
    }

    override fun showHome() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<MovieListFragment>(R.id.main_fragment_container_view)
        }
    }

    override fun showHistory() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<BookingHistoryFragment>(R.id.main_fragment_container_view)
        }
    }

    override fun showSettings() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace<SettingsFragment>(R.id.main_fragment_container_view)
        }
    }

    override fun showBottomSheetForHome() {
        binding.navigation.selectedItemId = R.id.navigation_home
    }

    override fun showBottomSheetForHistory() {
        binding.navigation.selectedItemId = R.id.navigation_history
    }

    override fun showBottomSheetForSettings() {
        binding.navigation.selectedItemId = R.id.navigation_settings
    }

    private fun restoreDestination(): Destination? {
        return intent.intentSerializable(EXTRA_DESTINATION, Destination::class.java)
    }

    private fun setBottomNavigationView() {
        binding.navigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    showHome()
                    true
                }

                R.id.navigation_history -> {
                    showHistory()
                    true
                }

                R.id.navigation_settings -> {
                    showSettings()
                    true
                }

                else -> false
            }
        }
    }

    private fun updateBottomNavigation() {
        val activeFragment =
            supportFragmentManager.findFragmentById(R.id.main_fragment_container_view)
                ?: movieListFragment
        when (activeFragment) {
            is MovieListFragment -> binding.navigation.selectedItemId = R.id.navigation_home
            is SettingsFragment -> binding.navigation.selectedItemId = R.id.navigation_settings
            is BookingHistoryFragment -> binding.navigation.selectedItemId = R.id.navigation_history
        }
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val EXTRA_DESTINATION = "EXTRA_DESTINATION"

        fun newIntent(
            context: Context,
            destination: Destination,
        ): Intent =
            Intent(context, MovieBookingActivity::class.java).apply {
                putExtra(EXTRA_DESTINATION, destination)
            }
    }
}
