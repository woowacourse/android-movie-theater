package woowacourse.movie.model

data class Theater(
    val id: Long,
    val rowRate: Map<Tier, List<Int>>,
    private val colLength: Int,
    val name: String,
) {
    constructor(id: Long, name: String) : this(
        id = id,
        rowRate = defaultRowRate,
        colLength = 4,
        name = name,
    )

    val seats =
        Seats(
            rowRate.flatMap { (tier, rateRows) ->
                rateRows.flatMap { row ->
                    (0 until colLength).map { col ->
                        Seat(tier, row - 1, col)
                    }
                }
            },
        )

    companion object {
        private val defaultRowRate = mapOf(Tier.S to listOf(3, 4), Tier.A to listOf(5), Tier.B to listOf(1, 2))
        val STUB_A =
            Theater(
                1,
                "잠실 극장",
            )
        val STUB_B =
            Theater(
                2,
                "선릉 극장",
            )
        val STUB_C =
            Theater(
                3,
                "강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남 ",
            )
    }
}
