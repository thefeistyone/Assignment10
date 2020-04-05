class ImageButton {
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
  color labelColor = 0;
  PImage background;
  
  ImageButton(String nameIn, float widthIn, float heightIn, float borderIn, float xIn, float yIn, String img) {
    name = nameIn;
    wide = widthIn;
    high = heightIn;
    borderRadius = borderIn;
    x = xIn;
    y = yIn;
    background = loadImage(img);
    background.resize(200, 0);
  }
  void setRadius(float r) {
    borderRadius = r;
  }
  void setLabelColor(color labelColorIn) {
    labelColor = labelColorIn;
  }
  void setLabel(String nameIn) {
    name = nameIn;
  }
  void setInActiveColor(color inActiveIn) {
    inActive = inActiveIn;
  }
  void setActiveColor(color activeIn) {
    active = activeIn;
  }
  void setHoverColor(color hoverIn) {
    hover = hoverIn;
  }
  void setSize(float widthIn, float heightIn) {
    wide = widthIn;
    high = heightIn;
  }
  void setPosition(float xIn, float yIn) {
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
    textSize(18);
    noStroke();
    fill(255);
    if (state) {
      rect(x, y, wide, high, borderRadius);
      image(background, x, y, background.width, background.height);
    } else if (!state && this.hover()) {
      rect(x, y, wide, high, borderRadius);
      image(background, x - background.width * 0.05, y - background.height * 0.05, background.width * 1.1, background.height * 1.1);
    } else {
      rect(x, y, wide, high, borderRadius);
      image(background, x, y, background.width, background.height);
    }
    fill(labelColor);
    textAlign(CENTER, TOP);
    text(name, x + wide/2 , y + high);
  }
}
//it aint easy being superior
