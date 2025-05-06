package woowacourse.movie.view.reservationComplete

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import woowacourse.movie.MainActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.presenter.reservationComplete.ReservationCompleteContracts
import woowacourse.movie.presenter.reservationComplete.ReservationCompletePresenter
import woowacourse.movie.view.extension.getSerializableExtraData

class ReservationCompleteActivity :
    androidx.appcompat.app.AppCompatActivity(),
    ReservationCompleteContracts.View {
    private val presenter: ReservationCompleteContracts.Presenter =
        ReservationCompletePresenter(this)
    private lateinit var binding: ActivityReservationCompleteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_complete)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.reservation_complete)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        presenter.updateTicketData(intent.getSerializableExtraData<MovieTicket>(TICKET_DATA_KEY))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupBackPressedDispatcher()
    }

    private fun setupBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    startActivity(MainActivity.getIntent(this@ReservationCompleteActivity))
                    finish()
                }
            },
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(MainActivity.getIntent(this))
        finish()
        return super.onSupportNavigateUp()
    }

    override fun showMovieTicket(movieTicket: MovieTicket) {
        binding.movieTicket = movieTicket
    }

    companion object {
        private const val TICKET_DATA_KEY = "movieTicket"

        fun getIntent(
            context: Context,
            movieTicket: MovieTicket,
        ): Intent =
            Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra(TICKET_DATA_KEY, movieTicket)
            }
    }
}
