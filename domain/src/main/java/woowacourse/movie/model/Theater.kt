package woowacourse.movie.model

class Theater(
    val id: Long,
    private val rowRate: Map<Tier, List<Int>>,
    private val colLength: Int,
    val name: String,
) {
    fun seats(): List<Seat> =
        rowRate.flatMap { (seatRate, rateRows) ->
            rateRows.flatMap { row ->
                (0 until colLength).map { col ->
                    Seat(seatRate, row - 1, col)
                }
            }
        }

    companion object {
        val STUB_A =
            Theater(
                0,
                mapOf(Tier.S to listOf(3, 4), Tier.A to listOf(5), Tier.B to listOf(1, 2)),
                4,
                "잠실 극장",
            )
        val STUB_B =
            Theater(
                1,
                mapOf(Tier.S to listOf(3, 4), Tier.A to listOf(5), Tier.B to listOf(1, 2)),
                4,
                "선릉 극장",
            )
        val STUB_C =
            Theater(
                2,
                mapOf(Tier.S to listOf(3, 4), Tier.A to listOf(5), Tier.B to listOf(1, 2)),
                4,
                "강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남강남 ",
            )
    }
}
