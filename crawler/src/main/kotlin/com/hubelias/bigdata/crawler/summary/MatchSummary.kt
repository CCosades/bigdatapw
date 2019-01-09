package com.hubelias.bigdata.crawler.summary


data class MatchSummary(
        val events: List<MatchEvent>
)

sealed class MatchEvent() {
    class Goal(val minute: Int,
               val player: String,
               val ownGoal: Boolean,
               val teamScored: String,
               val teamConceeded: String)

    class YellowCard(val minute: Int,
                     val player: String)

    class RedCard(val minute: Int,
                  val player: String)

    class Substitution(val minute: Int,
                       val playerOut: String,
                       val playerIn: String,
                       val team: String)

}

enum class GamePeriod {
    FIRST_HALF, SECOND_HALF, EXTRA_TIME_FIRST_HALF, EXTRA_TIME_SECOND_HALF
}