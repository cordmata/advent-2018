package com.github.cordmata.advent.day4

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.streams.toList

internal object Resources

sealed class Action
data class ShiftStart(val guardId: Int) : Action()
object FallsAsleep : Action()
object WakesUp : Action()
data class Nap(val from: LocalDateTime, val to: LocalDateTime)

data class LogEvent(val timestamp: LocalDateTime, val action: Action) {
  companion object {
    private val dateTimeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    private val logLinePattern = "^\\[(.*)] (.*)$".toRegex()
    private val shiftChangePattern = "^Guard #(\\d+) begins shift$".toRegex()
    fun fromString(logLine: String): LogEvent {
      val (ts, act) = logLinePattern.find(logLine)!!.destructured
      return LogEvent(LocalDateTime.parse(ts, dateTimeFormatter), when (act) {
        "wakes up" -> WakesUp
        "falls asleep" -> FallsAsleep
        else -> ShiftStart(shiftChangePattern.find(act)!!.groupValues[1].toInt())
      })
    }
  }
}

fun main() {
  val guardEvents = mutableMapOf<Int, MutableList<LogEvent>>().withDefault { mutableListOf() }
  var currentGuard = 0
  readLogFile().forEach {
    when (it.action) {
      is ShiftStart -> currentGuard = it.action.guardId
      else -> guardEvents[currentGuard]?.add(it)
    }
  }
  guardEvents.forEach { t, u -> println("$t -- $u")}
}

fun readLogFile(): List<LogEvent> {
  return Resources.javaClass.getResourceAsStream("/day4-guard-sleep.txt").bufferedReader().lines().map {
    LogEvent.fromString(it)
  }.toList().sortedBy { it.timestamp }
}
