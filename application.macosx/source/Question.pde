class Question {
  boolean active = false;
  boolean validInput = false;
  int questionNum;
  String message = "";
  double [] data;
  PImage pic;

  ImageButton mainButton;
  Input myInput;
  Output myOutput;
  Steps mySteps;


  Question(int numInputs, int qNum, String [] inputPrompts, int x, int y, String img, String solution) {
    questionNum = qNum;
    data = new double[numInputs];
    mainButton = new ImageButton("Question " + questionNum, 200, 100, 10, x, y, img);
    myInput = new Input(numInputs, inputPrompts);
    myOutput = new Output();
    mySteps = new Steps(solution);
    pic = loadImage(img);
    pic.resize(300, 0);
  }

  void update() {

    //if the question is selected
    if (active) {


      if (!showSteps) {
        showQNum();
        pic.resize(300, 0);
        image(pic, 490, 50);
      }

      if (input) {

        if (questionNum == 1) {
          textSize(16);
          textAlign(LEFT, TOP);
          fill(0);
          text("This is a multiple choice question.\nHit enter to see the answers.", 10, 50);
        }

        myInput.update();
      } else if (output) {
        myOutput.update();
      } else if (showSteps) {
        fill(0);
        textSize(30);
        textAlign(LEFT, TOP);
        text("Question " + questionNum + " -", 10, 10);
        mySteps.update();
      }
    }
  }

  void mouseClicked() {

    //if this question is selected
    if (active) {

      if (input) {
        if (submit.hover()) {
          solve();
        } else {
          myInput.mouseClicked();
        }
      } else if (output) {
        myOutput.mouseClicked();
      } else if (showSteps) {
        mySteps.mouseClicked();
      }
    }
  }

  void keyPressed() {

    //if the question is selected
    if (active) {

      //if the ENTER or RETURN key is pressed, solve the problem
      if (key == ENTER || key == RETURN) {
        if (active && input) {
          solve();
        } else if ( active && output ) {
          output = false;
          showSteps = true;
          showSolutions();
        }
      }

      //otherwise pass the event down to the input
      else {
        myInput.keyPressed();
      }
    }
  }

  void getInput() {
    validInput = true;
    for (int i = 0; i < myInput.inputs.length; i++) {
      try {
        data[i] = Float.parseFloat(myInput.inputs[i].value);
      }
      catch(Exception e) {
        validInput = false;
        JOptionPane.showMessageDialog(null, "Please enter only numbers. Units are not necessary.", "INVALID INPUT!", JOptionPane.ERROR_MESSAGE);
        break;
      }
    }
  }

  void solve() {
    switch(questionNum) {

    case 1:
      getInput();
      if (validInput) {
        message = question1(data);
        input = false;
        output = true;
      }
      break;

    case 2:
      getInput();
      if (validInput) {
        message = question2(data);
        input = false;
        output = true;
      }
      break;

    case 3:
      getInput();
      if (validInput) {
        message = question3(data);
        input = false;
        output = true;
      }
      break;

    case 4:
      getInput();
      if (validInput) {
        message = question4(data);
        input = false;
        output = true;
      }
      break;

    case 5:
      getInput();
      if (validInput) {
        message = question5(data);
        input = false;
        output = true;
      }
      break;
    }
    myOutput.message = message;
  }

  void showQNum() {
    fill(0);
    textSize(30);
    textAlign(LEFT, TOP);
    text("Question " + questionNum + ":", 10, 10);
  }

  void reset() {
    active = false;
    myInput.reset();
    data = new double[myInput.inputs.length];
  }
}
