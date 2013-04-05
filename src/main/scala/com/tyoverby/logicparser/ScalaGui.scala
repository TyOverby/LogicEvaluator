package com.tyoverby.logicparser

import swing._
import BorderPanel.Position._

object ScalaGui extends SimpleSwingApplication {
  val root = new MainFrame {
    title = "Truth Table Generator"

    lazy val inputText = new TextArea {
      this.font = new Font(java.awt.Font.MONOSPACED, java.awt.Font.BOLD, 26)
    }

    lazy val compileButton = Button("Compile") {
      outputText.peer.setText {
        try {
          Runner.collectAll(inputText.peer.getText)
        }
        catch{
          case e: Throwable => e.getMessage
        }
      }
    }

    lazy val outputText = new TextArea {
      this.font = new Font(java.awt.Font.MONOSPACED, java.awt.Font.PLAIN, 18)
    }

    lazy val header = new BorderPanel {
      add(new ScrollPane() {
        contents = inputText
        this.preferredSize = new Dimension(300, 50)

      }, Center)
      add(compileButton, East)
    }

    lazy val body = new ScrollPane() {
      contents = outputText
      this.preferredSize = new Dimension(300, 600)
    }


    contents = new BorderPanel {
      add(header, North)
      add(body, Center)
    }
  }

  def top = root
}
