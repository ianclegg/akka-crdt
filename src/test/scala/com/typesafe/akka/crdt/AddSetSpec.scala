/**
 * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
 */

package com.typesafe.akka.crdt

import org.scalatest.WordSpec
import org.scalatest.matchers.MustMatchers

class AddSetSpec extends WordSpec with MustMatchers {
  val data1 = "data1"
  val data2 = "data2"
  val data3 = "data3"
  val data4 = "data4"

  "A AddSet" must {

    "be able to add data" in {
      val c1 = AddSet[String]()

      val c2 = c1 + data1
      val c3 = c2 + data2

      val c4 = c3 + data4
      val c5 = c4 + data3

      c5.state must contain (data1)
      c5.state must contain (data2)
      c5.state must contain (data3)
      c5.state must contain (data4)
    }

    "be able to have its data set correctly merged with another AddSet with unique data sets" in {
      // set 1
      val c11 = AddSet[String]()

      val c12 = c11 + data1
      val c13 = c12 + data2

      c13.state must contain (data1)
      c13.state must contain (data2)

      // set 2
      val c21 = AddSet[String]()

      val c22 = c21 + data3
      val c23 = c22 + data4

      c23.state must contain (data3)
      c23.state must contain (data4)

      // merge both ways
      val merged1 = c13 merge c23
      merged1.state must contain (data1)
      merged1.state must contain (data2)
      merged1.state must contain (data3)
      merged1.state must contain (data4)

      val merged2 = c23 merge c13
      merged2.state must contain (data1)
      merged2.state must contain (data2)
      merged2.state must contain (data3)
      merged2.state must contain (data4)
    }

    "be able to have its data set correctly merged with another AddSet with overlapping data sets" in {
      // set 1
      val c10 = AddSet[String]()

      val c11 = c10 + data1
      val c12 = c11 + data2
      val c13 = c12 + data3

      c13.state must contain (data1)
      c13.state must contain (data2)
      c13.state must contain (data3)

      // set 2
      val c20 = AddSet[String]()

      val c21 = c20 + data2
      val c22 = c21 + data3
      val c23 = c22 + data4

      c23.state must contain (data2)
      c23.state must contain (data3)
      c23.state must contain (data4)

      // merge both ways
      val merged1 = c13 merge c23
      merged1.state must contain (data1)
      merged1.state must contain (data2)
      merged1.state must contain (data3)
      merged1.state must contain (data4)

      val merged2 = c23 merge c13
      merged2.state must contain (data1)
      merged2.state must contain (data2)
      merged2.state must contain (data3)
      merged2.state must contain (data4)
    }
  }
}