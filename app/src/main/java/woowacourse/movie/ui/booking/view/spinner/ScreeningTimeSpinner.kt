package woowacourse.movie.ui.booking.view.spinner

import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import java.time.LocalTime

class ScreeningTimeSpinner(
    private val spinner: Spinner
) {
    fun updateAdapter(times: List<LocalTime>){
        spinner.adapter =
            ArrayAdapter(
                spinner.context,
                android.R.layout.simple_spinner_item,
                times
            )
    }

    fun setOnItemSelectedListener(listener: AdapterView.OnItemSelectedListener){
        spinner.onItemSelectedListener = listener
    }

    fun select(position:Int){
        spinner.setSelection(position)
    }
}