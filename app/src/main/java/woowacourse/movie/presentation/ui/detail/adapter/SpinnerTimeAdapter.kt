package woowacourse.movie.presentation.ui.detail.adapter

import android.content.Context
import android.widget.ArrayAdapter
import java.time.LocalTime

class SpinnerTimeAdapter(context: Context) :
    ArrayAdapter<LocalTime>(context, android.R.layout.simple_spinner_item) 
