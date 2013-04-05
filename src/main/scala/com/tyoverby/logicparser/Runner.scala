package com.tyoverby.logicparser

object Runner {
  def runWith(parsed: LogicToken, alphabetSet: Set[Alphabet], truths: List[Boolean], sb: StringBuilder): Boolean = {
    val env = new Env(alphabetSet.toList)(truths)
    sb.append(env.toString + "  " + env.eval(parsed)).append("\n")
    env.eval(parsed)
  }

  def makeHeader(alphabetSet: Set[Alphabet], parsed: LogicToken): String = {
    val sb = new StringBuilder

    sb.append(alphabetSet.map(_.symbol).map("  " + _ + "   | ").mkString.init)
    sb.append("|  " + parsed.toString)
    sb.append("\n")
    alphabetSet.foreach(_ => sb.append("------+-"))
    sb.deleteCharAt(sb.length - 1)
    sb.append("+" + "-" * (parsed.toString.length + 3))
    sb.append("\n")
    sb.toString()
  }

  def collectAll(str: String): String = {
    val sb = new StringBuilder

    val parsed = LogicParser.parse(str)
    val alphabetSet = Env.getAllAlphabet(parsed)

    println(parsed)

    sb.append(makeHeader(alphabetSet, parsed))

    val count = alphabetSet.size
    val high = math.pow(2, count).toInt - 1
    for (num <- (0 to high)) {
      val bools = intToBooleans(num, count)
      runWith(parsed, alphabetSet, bools, sb)
    }

    sb.toString()
  }


  def runAll(str: String) {
    println(collectAll(str))
  }

  def intToBooleans(number: Int, length: Int): List[Boolean] = {
    def helper(n: Int, len: Int): List[Boolean] = {
      len match {
        case 0 => Nil
        case _ => ((n & 1) == 1) :: helper(n >>> 1, len - 1)
      }
    }
    helper(number, length).reverse
  }
}
