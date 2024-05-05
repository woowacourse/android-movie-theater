package woowacourse.movie.feature.seatselection

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieSeatSelectionBinding
import woowacourse.movie.feature.result.MovieResultActivity
import woowacourse.movie.model.MovieGrade
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.util.BaseActivity
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_DATE
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_SEATS
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_TIME
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_SEAT_POSITIONS
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_NAME
import woowacourse.movie.util.formatSeat
import woowacourse.movie.util.formatSeatColumn
import woowacourse.movie.util.formatSeatRow

class MovieSeatSelectionActivity :
    BaseActivity<MovieSeatSelectionContract.Presenter>(),
    MovieSeatSelectionContract.View {
    private lateinit var binding: ActivityMovieSeatSelectionBinding
    private lateinit var movieSelectedSeats: MovieSelectedSeats
    private val tableSeats: List<TextView> by lazy {
        findViewById<TableLayout>(R.id.table_seat).children.filterIsInstance<TableRow>()
            .flatMap { tableRow ->
                tableRow.children.filterIsInstance<TextView>().toList()
            }.toList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_seat_selection)
        binding.activity = this
        movieSelectedSeats =
            MovieSelectedSeats(
                intent.getIntExtra(KEY_MOVIE_COUNT, INVALID_VALUE_MOVIE_COUNT),
            )

        presenter.loadMovieTitle(
            intent.getLongExtra(
                KEY_MOVIE_ID,
                INVALID_VALUE_MOVIE_ID,
            ),
        )
        presenter.loadTableSeats(movieSelectedSeats)
    }

    override fun initializePresenter() = MovieSeatSelectionPresenter(this)

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        val movieCount =
            savedInstanceState.getInt(
                KEY_MOVIE_COUNT,
                INVALID_VALUE_MOVIE_COUNT,
            )
        movieSelectedSeats = MovieSelectedSeats(movieCount)
        presenter.updateSelectedSeats(movieSelectedSeats)

        val selectedSeatPositions = savedInstanceState.getIntArray(KEY_SELECTED_SEAT_POSITIONS)
        setUpSelectedSeats(selectedSeatPositions)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun displayMovieTitle(movieTitle: String) {
        binding.seatTitleText = movieTitle
    }

    override fun setUpTableSeats(baseSeats: List<MovieSeat>) {
        tableSeats.forEachIndexed { index, view ->
            val seat = baseSeats[index]
            view.text = seat.formatSeat()
            view.setTextColor(ContextCompat.getColor(this, seat.grade.getSeatColor()))
            view.setOnClickListener {
                presenter.clickTableSeat(index)
            }
        }
    }

    override fun updateSeatBackgroundColor(
        index: Int,
        isSelected: Boolean,
    ) {
        val backGroundColor = if (isSelected) R.color.white else R.color.selected
        tableSeats[index].setBackgroundColor(ContextCompat.getColor(this, backGroundColor))
    }

    override fun displayDialog() {
        AlertDialog.Builder(this).setTitle("예매 확인").setMessage("정말 예매하시겠습니까?")
            .setPositiveButton("예매 완료") { _, _ -> presenter.clickPositiveButton() }
            .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }.setCancelable(false).show()
    }

    override fun updateSelectResult(movieSelectedSeats: MovieSelectedSeats) {
        binding.movieSelectedSeats = movieSelectedSeats
        binding.totalPrice = movieSelectedSeats.totalPrice()
    }

    override fun navigateToResultView(movieSelectedSeats: MovieSelectedSeats) {
        val seats =
            movieSelectedSeats.selectedSeats.joinToString(", ") { seat ->
                seat.row.formatSeatRow() + seat.column.formatSeatColumn()
            }

        Intent(this, MovieResultActivity::class.java).apply {
            putExtra(KEY_MOVIE_ID, intent?.getLongExtra(KEY_MOVIE_ID, INVALID_VALUE_MOVIE_ID))
            putExtra(KEY_MOVIE_DATE, intent?.getStringExtra(KEY_MOVIE_DATE))
            putExtra(KEY_MOVIE_TIME, intent?.getStringExtra(KEY_MOVIE_TIME))
            putExtra(KEY_MOVIE_COUNT, movieSelectedSeats.count)
            putExtra(KEY_MOVIE_SEATS, seats)
            putExtra(KEY_SELECTED_THEATER_NAME, intent?.getStringExtra(KEY_SELECTED_THEATER_NAME))
            startActivity(this)
        }
    }

    override fun showToastInvalidMovieIdError(throwable: Throwable) {
        Log.e(TAG, "invalid movie id - ${throwable.message}")
        showToast(R.string.invalid_movie_id)
        finish()
    }

    private fun showToast(
        @StringRes stringResId: Int,
    ) {
        Toast.makeText(this, resources.getString(stringResId), Toast.LENGTH_SHORT).show()
    }

    private fun setUpSelectedSeats(selectedPositions: IntArray?) {
        selectedPositions?.forEach { position ->
            presenter.clickTableSeat(position)
        }
    }

    private fun MovieGrade.getSeatColor(): Int {
        return when (this) {
            MovieGrade.B_GRADE -> R.color.b_grade
            MovieGrade.S_GRADE -> R.color.s_grade
            MovieGrade.A_GRADE -> R.color.a_grade
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val count = movieSelectedSeats.count
        outState.putInt(KEY_MOVIE_COUNT, count)

        val selectedPositions = movieSelectedSeats.getSelectedPositions()
        outState.putIntArray(KEY_SELECTED_SEAT_POSITIONS, selectedPositions)
    }

    companion object {
        private val TAG = MovieSeatSelectionActivity::class.simpleName
    }
}
