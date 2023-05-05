package woowacourse.movie.mapper

import com.woowacourse.domain.Counter
import woowacourse.movie.model.CounterModel

fun Counter.toPresentation() : CounterModel {
    return CounterModel(count)
}

fun CounterModel.toDomain() : Counter {
    return Counter(count)
}
