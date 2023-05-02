package woowacourse.movie.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.contract.ReservationResultContract
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.PriceViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.SeatsViewData
import woowacourse.movie.error.ActivityError.finishWithError
import woowacourse.movie.error.ViewError
import woowacourse.movie.presenter.ReservationResultPresenter
import woowacourse.movie.system.getSerializableCompat
import woowacourse.movie.view.widget.MovieController
import woowacourse.movie.view.widget.MovieView
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class ReservationResultActivity : AppCompatActivity(), ReservationResultContract.View {
    override val presenter: ReservationResultContract.Presenter = ReservationResultPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation_result)
        makeBackButton()

        initReservationResultView()
    }

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initReservationResultView() {
        val reservation =
            intent.extras?.getSerializableCompat<ReservationViewData>(ReservationViewData.RESERVATION_EXTRA_NAME)
                ?: return finishWithError(ViewError.MissingExtras(ReservationViewData.RESERVATION_EXTRA_NAME))

        presenter.initActivity(reservation)
    }

    override fun setMovieData(movie: MovieViewData) {
        MovieController.bind(
            movie = movie,
            MovieView(
                title = findViewById(R.id.movie_reservation_result_title),
            )
        )
    }

    override fun setReservationDetailData(reservationDetail: ReservationDetailViewData) {
        val date = findViewById<TextView>(R.id.movie_reservation_result_date)
        val dateFormat =
            DateTimeFormatter.ofPattern(date.context.getString(R.string.reservation_datetime_format))
        date.text = dateFormat.format(reservationDetail.date)
    }

    override fun setPriceData(price: PriceViewData) {
        val priceText = findViewById<TextView>(R.id.movie_reservation_result_price)
        val formattedPrice = NumberFormat.getNumberInstance(Locale.US).format(price.value)
        priceText.text = priceText.context.getString(R.string.reservation_price, formattedPrice)
    }

    override fun setSeatData(reservationDetail: ReservationDetailViewData, seats: SeatsViewData) {
        val peopleCount = findViewById<TextView>(R.id.movie_reservation_result_people_count)

        peopleCount.text = peopleCount.context.getString(
            R.string.reservation_people_count, reservationDetail.peopleCount, formatSeats(seats)
        )
    }

    private fun formatSeats(seats: SeatsViewData): String {
        return seats.value.joinToString {
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
