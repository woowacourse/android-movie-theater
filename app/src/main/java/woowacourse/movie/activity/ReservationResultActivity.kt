package woowacourse.movie.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.PriceViewData
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.SeatsViewData
import woowacourse.movie.view.error.ActivityError.finishWithError
import woowacourse.movie.view.error.ViewError
import woowacourse.movie.view.getSerializableCompat
import woowacourse.movie.view.widget.MovieController
import woowacourse.movie.view.widget.MovieView
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class ReservationResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_result)

        initReservationResultView()
    }

    private fun initReservationResultView() {
        makeBackButton()

        val reservation =
            intent.extras?.getSerializableCompat<ReservationViewData>(ReservationViewData.RESERVATION_EXTRA_NAME)
                ?: return finishWithError(ViewError.MissingExtras(ReservationViewData.RESERVATION_EXTRA_NAME))
        renderReservationDetail(reservation.reservationDetail, reservation.seats, reservation.price)

        renderMovie(reservation.movie)
    }

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun renderMovie(
        movie: MovieViewData
    ) {
        MovieController.bind(
            movie = movie,
            MovieView(
                title = findViewById(R.id.movie_reservation_result_title),
            )
        )
    }

    private fun renderReservationDetail(
        reservationDetail: ReservationDetailViewData,
        seats: SeatsViewData,
        price: PriceViewData
    ) {
        val date = findViewById<TextView>(R.id.movie_reservation_result_date)
        val peopleCount = findViewById<TextView>(R.id.movie_reservation_result_people_count)
        val priceText = findViewById<TextView>(R.id.movie_reservation_result_price)
        val dateFormat =
            DateTimeFormatter.ofPattern(date.context.getString(R.string.reservation_datetime_format))
        date.text = dateFormat.format(reservationDetail.date)

        peopleCount.text = peopleCount.context.getString(
            R.string.reservation_people_count, reservationDetail.peopleCount, formatSeats(seats)
        )

        val formattedPrice = NumberFormat.getNumberInstance(Locale.US).format(price.value)

        priceText.text = priceText.context.getString(R.string.reservation_price, formattedPrice)
    }

    private fun formatSeats(seats: SeatsViewData): String {
        return seats.seats.joinToString {
            getString(R.string.seat_row_column, it.rowCharacter, it.column + 1)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun from(
            context: Context,
            reservation: ReservationViewData
        ): Intent {
            return Intent(context, ReservationResultActivity::class.java).apply {
                putExtra(ReservationViewData.RESERVATION_EXTRA_NAME, reservation)
            }
        }
    }
}
