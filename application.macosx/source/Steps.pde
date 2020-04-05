class Steps {
  PImage solution;
  Steps(String steps) {
    solution = loadImage(steps);
    solution.resize(0, maxY - 60);
  }

  void update() {
    textAlign(LEFT, TOP);
    textSize(30);
    fill(0);
    text("Example Solution:", 200, 10);
    image(solution, 10, 50);

    back.update();
    creditsButton.update();
  }

  void mouseClicked() {
    if (back.hover()) {
      superReset();
      home = true;
    } else if (creditsButton.hover()) {
      superReset();
      credits = true;
    }
  }
}
