package woowacourse.movie.presentation.ui.detail.adapter

import android.content.Context
import android.widget.ArrayAdapter
import java.time.LocalDate

class SpinnerDateAdapter(context: Context) :
    ArrayAdapter<LocalDate>(context, android.R.layout.simple_spinner_item)
