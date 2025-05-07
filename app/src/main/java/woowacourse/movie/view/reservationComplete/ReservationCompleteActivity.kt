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
import woowacourse.movie.view.extension.showShortToast
import woowacourse.movie.view.mapper.Formatter.localDateToUi
import woowacourse.movie.view.mapper.Formatter.priceToUi
import woowacourse.movie.view.seatSelection.SeatSelectionFormatter.seatsToUi

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

        presenter.updateTicketData(
            intent.getSerializableExtraData<MovieTicket>(TICKET_DATA_KEY) ?: run {
                showShortToast("예상치 못한 오류로 영화 예매가 취소 되었습니다. 메인 화면으로 돌아갑니다.")
                startActivity(MainActivity.getIntent(this@ReservationCompleteActivity))
                return
            },
        )
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setupBackPressedDispatcher()
    }

    private fun setupBackPressedDispatcher() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    navigateToMainActivity()
                }
            },
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        navigateToMainActivity()
        return super.onSupportNavigateUp()
    }

    private fun navigateToMainActivity() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

    override fun showMovieTicket(movieTicket: MovieTicket) {
        binding.movieTicket = movieTicket
        showMovieTimeStamp(movieTicket)
        showTheaterSeats(movieTicket)
        showPrice(movieTicket)
    }

    private fun showPrice(movieTicket: MovieTicket) {
        val formatPrice: String = priceToUi(movieTicket.price)

        binding.tvReservationCompleteTicketPrice.text =
            getString(
                R.string.reservation_complete_ticket_price,
                formatPrice,
            )
    }

    private fun showTheaterSeats(movieTicket: MovieTicket) {
        val formatedSeats: String = seatsToUi(movieTicket.seats, ", ")
        binding.tvReservationCompleteTicketCount.text =
            getString(
                R.string.reservation_complete_seat_theater_name_info,
                movieTicket.seats.size,
                formatedSeats,
                movieTicket.theater.name,
            )
    }

    private fun showMovieTimeStamp(movieTicket: MovieTicket) {
        val formatMovieDate: String = localDateToUi(movieTicket.movieDate)
        val formatMovieTime: String = movieTicket.movieTime.value.toString()
        binding.tvReservationCompleteTimestamp.text =
            getString(
                R.string.reservation_complete_ticket_timestamp,
                formatMovieDate,
                formatMovieTime,
            )
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
