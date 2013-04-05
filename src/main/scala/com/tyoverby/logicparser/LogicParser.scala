package com.tyoverby.logicparser

import scala.util.parsing.combinator.RegexParsers
import scala.util.parsing.input.CharSequenceReader

object LogicParser extends RegexParsers {
  private[this] def alphabetSymbols: Parser[LogicToken] = "[a-zA-Z]".r ^^ {
    s => Alphabet(s.head)
  }

  private[this] val not_sym = "~|(not)|!".r
  private[this] def not: Parser[LogicToken] = (not_sym ~> exp) ^^ Not

  private[this] val and_sym = "&|(and)".r
  private[this] def and: Parser[LogicToken] = "(" ~> exp ~ and_sym ~ exp <~ ")" ^^ {
    case left ~ _ ~ right => And(left, right)
  }

  private[this] val or_sym = "\\||(or)".r
  private[this] def or: Parser[LogicToken] = "(" ~> exp ~ or_sym ~ exp <~ ")" ^^ {
    case left ~ _ ~ right => Or(left, right)
  }

  private[this] val xor_sym = "\\^|(xor)".r
  private[this] def xor: Parser[LogicToken] = "(" ~> exp ~ xor_sym ~ exp <~ ")" ^^ {
    case left ~ _ ~ right => Xor(left, right)
  }

  private[this] val impl_sym = "(->)|(implies)|(impl)".r
  private[this] def implies: Parser[LogicToken] = "(" ~> exp ~ impl_sym ~ exp <~ ")" ^^ {
    case left ~ _ ~ right => Implies(left, right)
  }

  private[this] val double_sym = "(<->)|(eq)".r
  private[this] def doubleImplies: Parser[LogicToken] = "(" ~> exp ~ double_sym ~ exp <~ ")" ^^ {
    case left ~ _ ~ right => DoubleImplies(left, right)
  }

  private[this] def exp: Parser[LogicToken] = not | alphabetSymbols  | and | or | xor | implies | doubleImplies

  private[this] def whole: Parser[LogicToken] = phrase(exp)

  def parse(str: String): LogicToken = {

    val parsed = whole(new CharSequenceReader(str))

    parsed match {
      case Success(result, _) => result
      case error@NoSuccess(_, _) => throw new RuntimeException(error.toString)
    }
  }
}

