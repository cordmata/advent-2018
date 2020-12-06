package com.github.cordmata.advent.day1

import org.junit.Test

import org.assertj.core.api.Assertions.*

class FrequencyDeltaTest {

  @Test
  fun testIntegerParsing() {
    assertThat(Integer.parseInt("-2")).isEqualTo(-2)
    assertThat(Integer.parseInt("+5")).isEqualTo(5)
    assertThat(Integer.valueOf("-2")).isEqualTo(-2)
    assertThat(Integer.valueOf("+5")).isEqualTo(5)
  }
}
