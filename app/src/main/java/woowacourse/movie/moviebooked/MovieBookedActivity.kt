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
import woowacourse.movie.data.Reservation
import woowacourse.movie.databinding.MovieBookedBinding

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
        val id = intent.getLongExtra(KEY_RESERVATION, 0)
        presenter.loadReservationInfo(id)
    }

    override fun showReservation(reservation: Reservation) {
        binding.reservation = reservation
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
        private const val KEY_RESERVATION = "reservation"

        fun newIntent(
            context: Context,
            id: Long,
        ): Intent {
            return Intent(context, MovieBookedActivity::class.java)
                .apply {
                    putExtra(KEY_RESERVATION, id)
                }
        }
    }
}
