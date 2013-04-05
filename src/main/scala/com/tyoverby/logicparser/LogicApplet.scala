package com.tyoverby.logicparser

import swing._

class LogicApplet extends Applet {
  val ui = new UI{
    def init() {}
    ScalaGui.root.contents.foreach(this.contents_=)
  }
}
