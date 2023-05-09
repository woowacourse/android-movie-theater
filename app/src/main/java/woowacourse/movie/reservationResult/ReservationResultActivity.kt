package woowacourse.movie.reservationResult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.common.error.ActivityError.finishWithError
import woowacourse.movie.common.error.ViewError
import woowacourse.movie.common.model.MovieViewData
import woowacourse.movie.common.model.PriceViewData
import woowacourse.movie.common.model.ReservationDetailViewData
import woowacourse.movie.common.model.ReservationViewData
import woowacourse.movie.common.model.SeatsViewData
import woowacourse.movie.common.system.getSerializableCompat
import woowacourse.movie.common.view.widget.MovieView
import woowacourse.movie.databinding.ActivityReservationResultBinding
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class ReservationResultActivity : AppCompatActivity(), ReservationResultContract.View {
    override lateinit var presenter: ReservationResultContract.Presenter
    private lateinit var binding: ActivityReservationResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reservation_result)
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

        presenter = ReservationResultPresenter(this, reservation)
    }

    override fun setMovieData(movie: MovieViewData) {
        MovieView(
            title = binding.movieReservationResultTitle,
        ).bind(movie)
    }

    override fun setReservationDetailData(reservationDetail: ReservationDetailViewData) {
        val dateFormat =
            DateTimeFormatter.ofPattern(getString(R.string.reservation_datetime_format))
        binding.movieReservationResultDate.text = dateFormat.format(reservationDetail.date)
    }

    override fun setPriceData(price: PriceViewData) {
        val formattedPrice = NumberFormat.getNumberInstance(Locale.US).format(price.value)
        binding.movieReservationResultPrice.text =
            getString(R.string.reservation_price, formattedPrice)
    }

    override fun setSeatData(
        reservationDetail: ReservationDetailViewData,
        seats: SeatsViewData,
        theaterName: String
    ) {
        binding.movieReservationResultPeopleCount.text = getString(
            R.string.reservation_people_count,
            reservationDetail.peopleCount,
            formatSeats(seats),
            theaterName
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
