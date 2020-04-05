class Settings {
  Slider MaxY;
  Button cancel;
  Button apply;

  Settings() {
    MaxY = new Slider(50, 130, 200, 20, 700, 1100);
    cancel = new Button("Cancel", 50, 25, 5, 50, 425);
    apply = new Button("Apply", 50, 25, 5, 375, 425);
  }

  void update() {
    textSize(30);
    fill(0);
    textAlign(LEFT, TOP);
    text("Settings:", 10, 10);

    textSize(16);
    text("Adjust the slider to the desired maximum window height.\nThis will only affect the solutions pages, the default value is 1000.", 10, 50);
    MaxY.update();
    back.update();
    apply.update();
    cancel.update();
    surface.setSize(800, MaxY.currentVal - 20);
    back.y = MaxY.currentVal - 75;
    cancel.y = MaxY.currentVal - 75;
    apply.y = MaxY.currentVal - 75;
  }

  void mousePressed() {
    MaxY.mousePressed();
    if (back.hover() || cancel.hover()) {
      superReset();
      home = true;
    } 
    //do not apply settings
    else if (cancel.hover()) {
      superReset();
      home = true;
    } 
    //apply settings
    else if (apply.hover()) {
      maxY = MaxY.currentVal;
      superReset();
      home = true;
      for (int i = 1; i < questions.length; i++) {
        questions[i].mySteps.solution.resize(0, (maxY - 60));
      }
    }
  }

  void mouseDragged() {
    MaxY.mouseDragged();
  }
}
