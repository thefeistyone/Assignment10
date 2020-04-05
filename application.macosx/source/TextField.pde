class TextField {
  float w, x, y, h = 20;
  color background = color(214, 210, 210);
  boolean state = false;
  String value = "";

  TextField(float wIn, float xIn, float yIn) {
    w = wIn;
    x = xIn;
    y = yIn;
  }

  boolean hover() {
    if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= h + y) {
      return true;
    } else {
      return false;
    }
  }

  void mouseClicked() {
    //if the textfield is clicked on it will become active
    if (hover()) {
      state = true;
    }
    //if anywhere else if clicked it will become inactive
    else {
      state = false;
    }
  }

  void keyPressed() {

    //if the textfield is active
    if (state) {

      //if the user presses delete or backspace, delete the last character in the textfield
      if (key == BACKSPACE || key == DELETE) {
        if (value.length() > 0) {
          value = value.substring(0, value.length() - 1);
        }
      } else {
        value += key;
      }
    }
  }

  void clear() {
    value = "";
  }

  void update() {
    textAlign(LEFT, TOP);
    fill(background);
    textSize(15);
    if (state) {
      stroke(0);
      rect(x, y, w, h, 5);
      fill(0);
      text(value, x + 5, y + 1.5);
      //covering overflow
      fill(255);
      noStroke();
      rect(x + w + 1, y, 400, h);
    } else {
      fill(background);
      noStroke();
      rect(x, y, w, h, 5);
      fill(0);
      text(value, x + 5, y + 1.5);
      //covering overflow
      fill(255);
      noStroke();
      rect(x + w + 1, y, 400, h);
    }
  }
}
