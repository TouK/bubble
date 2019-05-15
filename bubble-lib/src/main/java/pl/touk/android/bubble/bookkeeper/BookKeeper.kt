package pl.touk.android.bubble.bookkeeper


data class Stats(val mean: Double,
                 val median: Long,
                 val min: Long,
                 val max: Long,
                 val standardDeviation: Double)


data class StatsFloat(val mean: Double,
                      val median: Float,
                      val min: Float,
                      val max: Float,
                      val standardDeviation: Double)

class BookKeeper {

    fun calculate(data: Array<Long>): Stats {
        val mean = data.average()
        val median = data.sortedArray().let { (it[499] + it[500])/2 }
        val sdSum = data.fold(0.0) { accumulator, next -> accumulator + Math.pow(next - mean, 2.0) }
        val standardDeviation = Math.sqrt(sdSum / data.size)
        return Stats(mean, median, data.min()!!, data.max()!!, standardDeviation)
    }

    fun calculateF(data: Array<Float>): StatsFloat {
        val mean = data.average()
        val median = data.sortedArray().let { (it[499] + it[500])/2 }
        val sdSum = data.fold(0.0) { accumulator, next -> accumulator + Math.pow(next - mean, 2.0) }
        val standardDeviation = Math.sqrt(sdSum / data.size)
        return StatsFloat(mean, median, data.min()!!, data.max()!!, standardDeviation)
    }
}
