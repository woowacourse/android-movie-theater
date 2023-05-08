package woowacourse.movie.ui.moviedetail

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.model.MovieListModel.MovieModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.ui.moviedetail.presenter.MovieDetailContract
import woowacourse.movie.ui.moviedetail.presenter.MovieDetailPresenter
import woowacourse.movie.utils.getSerializableExtraCompat
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MovieDetailActivity : AppCompatActivity(), MovieDetailContract.View {
    private lateinit var binding: ActivityMovieDetailBinding

    override val presenter: MovieDetailContract.Presenter by lazy {
        MovieDetailPresenter(this, this)
    }
    override val dateTimeSpinnerView by lazy {
        DateTimeSpinnerView(binding.detailDateSpinner, binding.detailTimeSpinner)
    }
    override val peopleCountControllerView by lazy {
        with(binding) {
            PeopleCountControllerView(detailMinusButton, detailPlusButton, detailPeopleCount)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        presenter.getMovieData(intent)
        presenter.initSpinner()
        initPeopleCountController()
        loadSavedData(savedInstanceState)
    }

    private fun initPeopleCountController() {
        peopleCountControllerView.apply {
            setMinusButton()
            setPlusButton()
            setPeopleCountView()
        }
    }

    private fun loadSavedData(savedInstanceState: Bundle?) {
        dateTimeSpinnerView.setItemPosition(
            savedInstanceState?.getInt(KEY_DATE_POSITION) ?: 0,
            savedInstanceState?.getInt(KEY_TIME_POSITION) ?: 0,
        )
        peopleCountControllerView.setPeopleCountNumber(
            savedInstanceState?.getSerializableExtraCompat<PeopleCountModel>(KEY_PEOPLE_COUNT)?.count
                ?: 1,
        )
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        setOutState(outState)
    }

    private fun setOutState(outState: Bundle) {
        outState.apply {
            putInt(KEY_DATE_POSITION, dateTimeSpinnerView.dateSelectedPosition)
            putInt(KEY_TIME_POSITION, dateTimeSpinnerView.timeSelectedPosition)
            putSerializable(KEY_PEOPLE_COUNT, peopleCountControllerView.peopleCountModel)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun setMovieInfo(movie: MovieModel) {
        with(binding) {
            detailPoster.setImageResource(movie.poster)
            detailTitle.text = movie.title
            detailDate.text =
                getString(R.string.screening_date, movie.startDate.format(), movie.endDate.format())
            detailRunningTime.text = getString(R.string.running_time, movie.runningTime)
            detailDescription.text = movie.description
        }
    }

    override fun setEventOnBookingButton(movingToSeatSelectionActivity: () -> Unit) {
        binding.detailBookingButton.setOnClickListener { movingToSeatSelectionActivity() }
    }

    private fun LocalDate.format(): String =
        format(DateTimeFormatter.ofPattern(getString(R.string.date_format)))

    companion object {
        private const val KEY_DATE_POSITION = "date_position"
        private const val KEY_TIME_POSITION = "time_position"
        private const val KEY_PEOPLE_COUNT = "count"
    }
}
