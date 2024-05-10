package woowacourse.movie.presentation.view.reservation.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieDetailBinding
import woowacourse.movie.presentation.base.BaseActivity
import woowacourse.movie.presentation.uimodel.MovieUiModel
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectionActivity
import woowacourse.movie.presentation.view.screening.ScreeningActivity.Companion.MOVIE_ID_KEY
import woowacourse.movie.presentation.view.screening.theater.TheaterBottomSheetDialogFragment.Companion.THEATER_ID_KEY

class MovieDetailActivity : BaseActivity(), MovieDetailContract.View, MovieDetailContract.ViewActions {
    private lateinit var movieDetailPresenter: MovieDetailContract.Presenter
    private lateinit var timeSpinnerAdapter: ArrayAdapter<String>
    private lateinit var binding: ActivityMovieDetailBinding

    override fun getLayoutResId(): Int = R.layout.activity_movie_detail

    override fun onCreateSetup(savedInstanceState: Bundle?) {
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val movieId = intent.getIntExtra(MOVIE_ID_KEY, DEFAULT_MOVIE_ID)
        val theaterId = intent.getIntExtra(THEATER_ID_KEY, DEFAULT_THEATER_ID)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail)
        binding.view = this

        movieDetailPresenter = MovieDetailPresenterImpl(movieId, theaterId)
        movieDetailPresenter.attachView(this)

        savedInstanceState?.let { it ->
            val count = it.getString(SIS_COUNT_KEY)?.toIntOrNull()
            count?.let { value ->
                movieDetailPresenter.initReservationCount(value)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        movieDetailPresenter.detachView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SIS_COUNT_KEY, binding.reservationCount.text.toString())
    }

    override fun showMovieDetails(movieUiModel: MovieUiModel) {
        binding.movieDetails = movieUiModel
        updateBinding()
    }

    override fun setScreeningDates(
        dates: List<String>,
        defaultDataIndex: Int,
    ) {
        attachDateSpinnerAdapter(dates, defaultDataIndex)
        attachTimeSpinnerAdapter(mutableListOf(), defaultDataIndex)
    }

    private fun attachDateSpinnerAdapter(
        dates: List<String>,
        defaultDataIndex: Int,
    ) {
        val adapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, dates).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        binding.dateSpinner.adapter = adapter
        binding.dateSpinner.setSelection(defaultDataIndex)
        onSelectDateListener()
    }

    override fun updateScreeningTimes(
        times: List<String>,
        defaultDataIndex: Int,
    ) {
        timeSpinnerAdapter.clear()
        timeSpinnerAdapter.addAll(times)
        timeSpinnerAdapter.notifyDataSetChanged()
        binding.timeSpinner.setSelection(defaultDataIndex)
    }

    override fun updateReservationCount(count: Int) {
        binding.reservationCount.text = count.toString()
    }

    private fun attachTimeSpinnerAdapter(
        times: MutableList<String>,
        defaultDataIndex: Int,
    ) {
        timeSpinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, times).apply {
                setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            }
        binding.timeSpinner.adapter = timeSpinnerAdapter
        binding.timeSpinner.setSelection(defaultDataIndex)
        onSelectTimeListener()
    }

    private fun onSelectDateListener() {
        binding.dateSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    val selectedDate = parent.getItemAtPosition(position) as String
                    movieDetailPresenter.selectDate(selectedDate)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    binding.dateSpinner.setSelection(DEFAULT_SPINNER_INDEX)
                }
            }
    }

    private fun onSelectTimeListener() {
        binding.timeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long,
                ) {
                    val selectedTime = parent.getItemAtPosition(position) as String
                    movieDetailPresenter.selectTime(selectedTime)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    binding.timeSpinner.setSelection(DEFAULT_SPINNER_INDEX)
                }
            }
    }

    override fun onPlusButtonClicked() {
        movieDetailPresenter.plusReservationCount()
        updateBinding()
    }

    override fun onMinusButtonClicked() {
        movieDetailPresenter.minusReservationCount()
        updateBinding()
    }

    override fun onReserveButtonClicked() {
        movieDetailPresenter.onReserveButtonClicked()
    }

    private fun updateBinding() {
        binding.invalidateAll()
    }

    override fun moveToSeatSelection(
        reservationCount: Int,
        title: String,
    ) {
        val intent = Intent(this, SeatSelectionActivity::class.java)
        intent.putExtra(TITLE_KEY, title)
        intent.putExtra(RESERVATION_COUNT_KEY, reservationCount)
        startActivity(intent)
    }

    companion object {
        const val DEFAULT_MOVIE_ID = -1
        const val DEFAULT_THEATER_ID = -1
        const val SIS_COUNT_KEY = "count"
        const val DEFAULT_SPINNER_INDEX = 0
        const val TITLE_KEY = "title"
        const val RESERVATION_COUNT_KEY = "reservationCount"
    }
}
