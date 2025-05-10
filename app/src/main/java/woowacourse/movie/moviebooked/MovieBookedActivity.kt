package woowacourse.movie.moviebooked

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.MovieBookedBinding
import woowacourse.movie.domain.ReservationInfo
import woowacourse.movie.helper.BuildVersion

class MovieBookedActivity : AppCompatActivity(), MovieBookedContract.View {
    private lateinit var binding: MovieBookedBinding
    private lateinit var presenter: MovieBookedPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        initBinding()
        applyWindowInserts()
        presenter = MovieBookedPresenter(this)
        fetchReservationInfo()
    }

    override fun fetchReservationInfo() {
        val reservationInfo =
            BuildVersion().getParcelableClass(
                intent,
                KEY_RESERVATION_INFO,
                ReservationInfo::class,
            )
        presenter.loadReservationInfo(reservationInfo)
    }

    override fun showReservationInfo(reservationInfo: ReservationInfo) {
        binding.reservationInfo = reservationInfo
    }

    private fun initBinding() {
        binding = DataBindingUtil.setContentView(this, R.layout.movie_booked)
    }

    private fun applyWindowInserts() {
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.booked_root)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    companion object {
        private const val KEY_RESERVATION_INFO = "reservationInfo"

        fun newIntent(
            context: Context,
            reservationInfo: ReservationInfo,
        ): Intent {
            return Intent(context, MovieBookedActivity::class.java)
                .apply {
                    putExtra(KEY_RESERVATION_INFO, reservationInfo)
                }
        }
    }
}
