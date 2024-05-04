package woowacourse.movie.seatselection.view

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import woowacourse.movie.R
import woowacourse.movie.databinding.ActivityMovieSeatSelectionBinding
import woowacourse.movie.model.MovieGrade
import woowacourse.movie.model.MovieSeat
import woowacourse.movie.model.MovieSelectedSeats
import woowacourse.movie.result.view.MovieResultActivity
import woowacourse.movie.seatselection.presenter.MovieSeatSelectionPresenter
import woowacourse.movie.seatselection.presenter.contract.MovieSeatSelectionContract
import woowacourse.movie.seatselection.view.listener.MovieSeatSelectionClickListener
import woowacourse.movie.util.Formatter.formatColumn
import woowacourse.movie.util.Formatter.formatRow
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_DATE
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_SEATS
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_TIME
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_SEAT_POSITIONS
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_NAME

class MovieSeatSelectionActivity :
    AppCompatActivity(),
    MovieSeatSelectionContract.View,
    MovieSeatSelectionClickListener {
    private lateinit var binding: ActivityMovieSeatSelectionBinding
    private lateinit var seatSelectionPresenter: MovieSeatSelectionPresenter

    private val tableSeats: List<TextView> by lazy {
        findViewById<TableLayout>(R.id.seatTable).children.filterIsInstance<TableRow>()
            .flatMap { tableRow ->
                tableRow.children.filterIsInstance<TextView>().toList()
            }.toList()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding = ActivityMovieSeatSelectionBinding.inflate(layoutInflater)
        binding.clickListener = this
        setContentView(binding.root)

        seatSelectionPresenter =
            MovieSeatSelectionPresenter(this)
        seatSelectionPresenter.loadMovieTitle(
            intent.getLongExtra(
                KEY_MOVIE_ID,
                INVALID_VALUE_MOVIE_ID,
            ),
        )
        seatSelectionPresenter.loadTableSeats(
            intent.getIntExtra(
                KEY_MOVIE_COUNT,
                INVALID_VALUE_MOVIE_COUNT,
            ),
        )
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val count = seatSelectionPresenter.movieSelectedSeats.count
        outState.putInt(KEY_MOVIE_COUNT, count)

        val selectedPositions = seatSelectionPresenter.movieSelectedSeats.getSelectedPositions()
        outState.putIntArray(KEY_SELECTED_SEAT_POSITIONS, selectedPositions)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        val savedCount =
            savedInstanceState.getInt(
                KEY_MOVIE_COUNT,
                INVALID_VALUE_MOVIE_COUNT,
            )
        seatSelectionPresenter.updateSelectedSeats(savedCount)

        val selectedPositions = savedInstanceState.getIntArray(KEY_SELECTED_SEAT_POSITIONS)
        setUpSelectedSeats(selectedPositions)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    override fun displayMovieTitle(movieTitle: String) {
        binding.movieTitle = movieTitle
    }

    override fun setUpTableSeats(baseSeats: List<MovieSeat>) {
        tableSeats.forEachIndexed { index, view ->
            val seat = baseSeats[index]
            view.text = getString(R.string.seat, seat.row.formatRow(), seat.column.formatColumn())
            view.setTextColor(ContextCompat.getColor(this, seat.grade.getSeatColor()))
            view.setOnClickListener {
                seatSelectionPresenter.clickTableSeat(index)
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

    override fun updateSelectResult(movieSelectedSeats: MovieSelectedSeats) {
        binding.movieSelectedSeats = movieSelectedSeats
    }

    override fun navigateToResultView(movieSelectedSeats: MovieSelectedSeats) {
        val seats =
            movieSelectedSeats.selectedSeats.map { seat ->
                (seat.row.formatRow() + seat.column.formatColumn())
            }.joinToString(", ")

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

    private fun setUpSelectedSeats(selectedPositions: IntArray?) {
        selectedPositions?.forEach { position ->
            seatSelectionPresenter.clickTableSeat(position)
        }
    }

    private fun MovieGrade.getSeatColor(): Int {
        return when (this) {
            MovieGrade.B_GRADE -> R.color.b_grade
            MovieGrade.S_GRADE -> R.color.s_grade
            MovieGrade.A_GRADE -> R.color.a_grade
        }
    }

    override fun onCompleteButtonClick() {
        AlertDialog.Builder(this).setTitle("예매 확인").setMessage("정말 예매하시겠습니까?")
            .setPositiveButton("예매 완료") { _, _ -> seatSelectionPresenter.clickPositiveButton() }
            .setNegativeButton("취소") { dialog, _ -> dialog.dismiss() }.setCancelable(false).show()
    }
}
