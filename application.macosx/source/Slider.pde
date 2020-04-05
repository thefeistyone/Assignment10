class Slider {
  float x;
  float y;
  float w;
  float h;
  float min;
  float max;
  color inActive = color(23, 20, 184);
  color active = color(30, 184, 200);
  color backgroundColor = color (255);
  color labelColor = color(0);
  float slideWidth = 15;
  float radius = 10;
  float slideX;
  float offSet;
  boolean activeBool = false;
  int currentVal;

  Slider(float xIn, float yIn, float widthIn, float heightIn, float minIn, float maxIn) {
    x = xIn;
    y = yIn;
    w = widthIn;
    h = heightIn;
    min = minIn;
    max = maxIn;
    slideX = xIn;
    currentVal = (int) min;
  }

  boolean hover() {
    if (mouseX >= slideX && mouseX <= slideX + slideWidth && mouseY >= y - 10 && mouseY < y + h + 10) {
      return true;
    } else {
      return false;
    }
  }

  void mouseDragged() {
    if (activeBool) {
      if (mouseX - offSet < x + w) {
        slideX = mouseX - offSet;
      }
    }
  }
  
  void mousePressed() {
    if(hover()) {
      this.activeBool = true;
      offSet = mouseX - slideX;
    }
  }

  void update() {
    noStroke();
    fill(inActive);
    rect(x, y, w, h, radius);

    //setting left bound
    if (slideX < x) {
      slideX = x;
    }
    //setting right bound
    if (slideX + slideWidth > x + w) {
      slideX = x + w - slideWidth;
    }

    //if mouseover slider
    if ((hover())) {
      fill(active);
      rect(slideX, y - 10, slideWidth, h + 20, radius);
    } else {
      fill(inActive);
      rect(slideX, y - 10, slideWidth, h + 20, radius);
    }

    //setting current min and max based on position of slider
    currentVal = (int) map(slideX, x, x + w - slideWidth, min, max);

    //printing value
    fill(labelColor);
    textAlign(LEFT, TOP);
    textSize(16);
    text(currentVal, x, y + h + 10);
  }
}
//it aint easy being superior
