class Input {
  TextField [] inputs;
  int currentField = 0;
  String [] inputStrings;

  Input(int numInputs, String [] inputPrompts) {
    inputStrings = inputPrompts;
    inputs = new TextField[numInputs];
    for (int i = 0; i < inputs.length; i++) {
      inputs[i] = new TextField(70, 10, 80 + 50 * i);
    }
    if (numInputs > 0) {
      inputs[0].state = true;
    }
  }

  void update() {

    // showing input page
    for (int i = 0; i < inputs.length; i++) {
      inputs[i].update();
      fill(0);
      textSize(16);
      text(inputStrings[i], 10, 60 + 50 * i);
    }
    //updating input page buttons
    creditsButton.update();
    submit.update();
    back.update();

    //updating all text fields
    for (int i = 0; i < inputs.length; i++) {
      inputs[i].update();
    }
  }

  void keyPressed() {

    //if the tab or down arrow is pressed activate the next texfield
    if (key == TAB || keyCode == DOWN) {

      //deactivate current textfield
      inputs[currentField].state = false;

      //try going to the next element and activating it
      try {
        inputs[currentField + 1].state = true;
        currentField++;
      }

      //if this raised indexoutofboundserror activate the first textfield 
      catch(Exception e) {
        inputs[0].state = true;
        currentField = 0;
      }
    } 

    //if the up arrow is pressed select the previous textfield
    else if (keyCode == UP) {

      //deactivate current textfield
      inputs[currentField].state = false;

      //if first textfield is active, activate last textfield
      if (currentField == 0) {
        inputs[inputs.length - 1].state = true;
        currentField = inputs.length - 1;
      } 

      //otherwise activate previous textfield
      else {
        inputs[currentField - 1].state = true;
        currentField--;
      }
    }

    //if another key is pressed, pass the event down to the textfields
    else {
      for (int i = 0; i < inputs.length; i++) {
        inputs[i].keyPressed();
      }
    }
  }

  void mouseClicked() {

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

    //otherwise pass event down to textfields
    else {
      for (int i = 0; i < inputs.length; i++) {
        inputs[i].mouseClicked();
      }
    }
  }

  void reset() {
    try {
      inputs[0].state = true;
      inputs[0].clear();
    }
    catch(Exception e) {
    }
    for (int i = 1; i < inputs.length; i++) {
      inputs[i].state = false;
      inputs[i].clear();
      currentField = 0;
    }
  }
}
