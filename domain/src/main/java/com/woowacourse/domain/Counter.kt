package com.woowacourse.domain

@JvmInline
value class Counter(val count: Int) {

    init {
        require(count >= MIN_COUNT) { "최소 count의 값은 $MIN_COUNT 이어야 합니다." }
    }

    fun add(): Counter {
        return Counter(count + 1)
    }

    fun sub(): Counter {
        if (count > MIN_COUNT)
            return Counter(count - 1)
        return this
    }

    companion object {
        private const val MIN_COUNT = 1
    }
}
