package woowacourse.movie.activity.reservationresult

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.databinding.ActivityReservationResultBinding
import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.error.ActivityError.finishWithError
import woowacourse.movie.view.error.ViewError
import woowacourse.movie.view.getSerializable

class ReservationResultActivity : AppCompatActivity(), ReservationResultContract.View {

    override lateinit var presenter: ReservationResultContract.Presenter
    private val binding: ActivityReservationResultBinding by lazy {
        ActivityReservationResultBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        presenter = ReservationResultPresenter(this)
        initReservationResultView()
    }

    private fun initReservationResultView() {
        makeBackButton()

        val reservation: ReservationViewData = getIntentReservationData() ?: return finishWithError(
            ViewError.MissingExtras(ReservationViewData.RESERVATION_EXTRA_NAME)
        )
        presenter.renderReservation(reservation)
        renderMovie(reservation.movie)
    }

    private fun getIntentReservationData(): ReservationViewData? =
        intent.extras?.getSerializable<ReservationViewData>(ReservationViewData.RESERVATION_EXTRA_NAME)

    private fun makeBackButton() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun renderMovie(
        movie: MovieViewData
    ) {
        binding.movieReservationResultTitle.text = movie.title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun renderReservation(date: String, peopleCount: String, price: String) {
        binding.movieReservationResultDate.text = date
        binding.movieReservationResultPeopleCount.text = peopleCount
        binding.movieReservationResultPrice.text = price
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
