package com.tyoverby.logicparser

import scala.collection.immutable.ListMap

class Env(alphabets: List[Alphabet])(predicates: List[Boolean]) {
  require(alphabets.length == predicates.length)

  val lookup = ListMap(alphabets.map(_.symbol).zip(predicates): _*)

  override val toString = lookup.values.map{
    case true =>  "true "
    case false => "false"
  }.mkString(" | ") + " || "

  def eval(token: LogicToken): Boolean = {
    token match {
      case Alphabet(x) => lookup(x)
      case Not(p) => !eval(p)
      case And(l, r) => eval(l) && eval(r)
      case Or(l,r) => eval(l) || eval(r)
      case Implies(l, r) => {
        val lv = eval(l)
        lazy val rv = eval(r)
        (!eval(l)) || (lv && rv)
      }
      case DoubleImplies(l, r) => eval(l) == eval(r)
      case Xor(l, r) => {
        val lv = eval(l)
        val rv = eval(r)
        (lv && !rv) || (!lv && rv)
      }
    }
  }
}

object Env {
  def getAllAlphabet(token: LogicToken): Set[Alphabet] = {
    token match {
      case x: Alphabet => Set(x)
      case Not(on) => getAllAlphabet(on)
      case x: Binary => getAllAlphabet(x.left) ++ getAllAlphabet(x.right)
    }
  }
}
