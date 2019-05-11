package com.github.cordmata.advent.day3

import java.awt.Point
import java.awt.Rectangle
import kotlin.streams.toList

internal object Resources

data class FabricClaim(
    val id: Int,
    val leftOffset: Int,
    val topOffset: Int,
    val width: Int,
    val height: Int) {
  private val rectangle = Rectangle(leftOffset, topOffset, width, height)
  fun intersects(other: FabricClaim): Boolean = this.rectangle.intersects(other.rectangle)
  fun intersection(other: FabricClaim): Rectangle = this.rectangle.intersection(other.rectangle)
}

fun main() {
  val claims = readClaims()
  val points = mutableSetOf<Point>()
  claims.forEach { c ->
    var intersectsAny = false
    claims.forEach{ o ->
      if (c.id != o.id && c.intersects(o)) {
        intersectsAny = true
        val intersect = c.intersection(o)
        for (h in intersect.y until intersect.y + intersect.height) {
          for (w in intersect.x until intersect.x + intersect.width) {
            points.add(Point(w, h))
          }
        }
      }
    }
    if (!intersectsAny) println(c.id)
  }
  println(points.size)
}

fun readClaims(): List<FabricClaim> {
  val claimParser = "^#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)$".toRegex()
  return Resources.javaClass.getResourceAsStream("/day3-fabric-plans.txt").bufferedReader().lines().map {
    val (id, leftOffset, topOffset, width, height) = claimParser.find(it)!!.destructured
    FabricClaim(
        id.toInt(),
        leftOffset.toInt(),
        topOffset.toInt(),
        width.toInt(),
        height.toInt()
    )
  }.toList()
}
