class Output {
  Button link;

  String message = "";

  Output() {
    link = new Button("Drive", 100, 25, 5, 10, 200);
  }

  void update() {

    //showing output page
    textSize(16);
    text(message, 10, 50);


    //special case of a multiple choice question
    if (questions[0].active) {
      link.update();
    }

    //showing output page buttons
    back.update();
    creditsButton.update();
    if (!questions[0].active) {
      solutions.update();
    }
  }

  void mouseClicked() {

    //special case of multiple choice question
    if (questions[0].active) {
      if (link.hover()) {
        link("https://docs.google.com/document/d/1Eai0BoIDdsFn2BNWj4JgnaBHVyCkcphQRAiWNpI6xdY/edit");
      }
    }
    //going back to home page, resetting all question state to false
    if (back.hover()) {
      superReset();
      home = true;
    }

    //going to credits page
    else if (creditsButton.hover()) {
      superReset();
      credits = true;
    } 
    //going to solutions page
    else if (solutions.hover() && !questions[0].active) {
      output = false;
      showSteps = true;
      showSolutions();
      surface.setSize(1000, 1000);
    }
  }
}
