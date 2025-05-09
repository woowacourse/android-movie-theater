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
import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.entity.ReservationInfoEntity
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.databinding.ActivityReservationCompleteBinding
import woowacourse.movie.presenter.reservationComplete.ReservationCompleteContracts
import woowacourse.movie.presenter.reservationComplete.ReservationCompletePresenter
import woowacourse.movie.view.extension.getSerializableExtraData
import woowacourse.movie.view.extension.showShortToast
import woowacourse.movie.view.mapper.Formatter.priceToUi
import woowacourse.movie.view.seatSelection.SeatSelectionFormatter.seatsToUi

class ReservationCompleteActivity :
    androidx.appcompat.app.AppCompatActivity(),
    ReservationCompleteContracts.View {
    private lateinit var presenter: ReservationCompleteContracts.Presenter
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

        presenter = ReservationCompletePresenter(this, AppDatabase.getDatabase(this))
        presenter.updateTicketData(
            intent.getSerializableExtraData<Long>(RESERVATION_ID_KEY) ?: run {
                showShortToast("없는 예약 번호 입니다.")
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

    override fun showMovieTicket(movieTicketEntity: MovieTicketEntity) {
        binding.tvReservationCompleteTitle.text = movieTicketEntity.movie.title
        showMovieTimeStamp(movieTicketEntity.reservationInfoEntity)
        showTheaterSeats(movieTicketEntity.seats, movieTicketEntity.theater.name)
        showPrice(movieTicketEntity.reservationInfoEntity.price)
    }

    private fun showMovieTimeStamp(reservationInfo: ReservationInfoEntity) {
        val movieDate =
            listOf(
                reservationInfo.movieDateYear,
                reservationInfo.movieDateMonth,
                reservationInfo.movieDateDay,
            ).joinToString(".")
        val movieTime =
            listOf(
                reservationInfo.movieTimeHour,
                reservationInfo.movieTimeMinute,
            ).joinToString(":")

        binding.tvReservationCompleteTimestamp.text =
            getString(
                R.string.reservation_complete_ticket_timestamp,
                movieDate,
                movieTime,
            )
    }

    private fun showTheaterSeats(
        seats: List<SeatEntity>,
        theaterName: String,
    ) {
        val formatedSeats: String = seatsToUi(seats, ", ")
        binding.tvReservationCompleteTicketCount.text =
            getString(
                R.string.reservation_complete_seat_theater_name_info,
                seats.size,
                formatedSeats,
                theaterName,
            )
    }

    private fun showPrice(price: Int) {
        binding.tvReservationCompleteTicketPrice.text =
            getString(
                R.string.reservation_complete_ticket_price,
                priceToUi(price),
            )
    }

    companion object {
        private const val RESERVATION_ID_KEY = "reservationIdKey"

        fun getIntent(
            context: Context,
            reservationDetailId: Long,
        ): Intent =
            Intent(context, ReservationCompleteActivity::class.java).apply {
                putExtra(RESERVATION_ID_KEY, reservationDetailId)
            }
    }
}
