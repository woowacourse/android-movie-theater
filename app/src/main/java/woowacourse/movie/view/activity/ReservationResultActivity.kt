package woowacourse.movie.view.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.contract.ReservationResultContract
import woowacourse.movie.data.MovieViewData
import woowacourse.movie.data.PriceViewData
import woowacourse.movie.data.ReservationDetailViewData
import woowacourse.movie.data.ReservationViewData
import woowacourse.movie.data.SeatsViewData
import woowacourse.movie.databinding.ActivityReservationResultBinding
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

        presenter.initActivity(reservation)
    }

    override fun setMovieData(movie: MovieViewData) {
        MovieController.bind(
            movie = movie,
            MovieView(
                title = binding.movieReservationResultTitle,
            )
        )
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
