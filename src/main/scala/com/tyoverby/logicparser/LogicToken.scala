package com.tyoverby.logicparser

sealed trait LogicToken

case class Alphabet(symbol: Char) extends LogicToken {
  override def toString = symbol.toString
}

case class Not(on: LogicToken) extends LogicToken {
  override def toString = "~" + on.toString
}

sealed trait Binary extends LogicToken {
  val symbol: String
  val left: LogicToken
  val right: LogicToken

  override def toString = "(" + left.toString + " " + symbol + " " + right.toString + ")"
}

case class And(left: LogicToken, right: LogicToken) extends Binary {
  val symbol = "&"
}

case class Xor(left: LogicToken, right: LogicToken) extends Binary {
  val symbol = "^"
}

case class Or(left: LogicToken, right: LogicToken) extends Binary {
  val symbol = "|"
}

case class Implies(left: LogicToken, right: LogicToken) extends Binary {
  val symbol = "->"
}

case class DoubleImplies(left: LogicToken, right: LogicToken) extends Binary {
  val symbol = "<->"
}
