class Button {
  String name = "";
  float wide = 100;
  float high = 100;
  float borderRadius = 0;
  float x = 0;
  float y = 0;
  boolean state = false;
  color inActive = color(23, 20, 184);
  color active = color(86, 196, 227);
  color hover = color(30, 184, 200);
  color labelColor = 255;
  Button(String nameIn, float widthIn, float heightIn, float borderIn, float xIn, float yIn) {
    name = nameIn;
    wide = widthIn;
    high = heightIn;
    borderRadius = borderIn;
    x = xIn;
    y = yIn;
  }

  boolean hover() {
    if (mouseX >= x && mouseX <= x + wide && mouseY >= y && mouseY <= high + y) {
      return true;
    } else {
      return false;
    }
  }
  void update() {
    textSize(16);
    textAlign(CENTER, CENTER);
    noStroke();
    if (state) {
      fill(active);
      rect(x, y, wide, high, borderRadius);
    } else if (!state && this.hover()) {
      fill(hover);
      rect(x, y, wide, high, borderRadius);
    } else {
      fill(inActive);
      rect(x, y, wide, high, borderRadius);
    }
    fill(labelColor);
    textAlign(CENTER, CENTER);
    text(name, x + wide/2 , y + high / 2 - 3);
  }
}
//it aint easy being superior
