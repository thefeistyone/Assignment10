import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import javax.swing.JOptionPane; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Assignment10 extends PApplet {


Question [] questions = new Question[5];
String [] q1Prompts = {};
String [] q2Prompts = {"Enter the value of a1 in m:", "Enter the value of a2 in m:", "Enter the value of a3 in m:", "Enter the value of F1 in N:", "Enter the value of F2 in N:", "Enter the value of M in Nm:"};
String [] q3Prompts = {"Enter the value of the length of the beam in m:", "Enter the value of q in N/m:", "Enter the value of w in N/m:"};
String [] q4Prompts = {"Enter the value of a in m:", "Enter the value of b in m:", "Enter the value of F in kN:", "Enter the value of M in kNm:", "Enter the value of w in kN/m:"};
String [] q5Prompts = {"Enter the value of a in m:", "Enter the value of b in m:", "Enter the value of w in kN/m:", "Enter the value of F in kN:", "Enter the value of M in kNm:"};

boolean home = true;
boolean credits = false;
boolean showQuestion = false;
boolean input = true;
boolean output = false;
boolean showSteps = false;
boolean settings = false;

int maxY = 1000;

Button submit = new Button("Submit", 60, 25, 5, 50, 425);
Button solutions = new Button("Show Steps", 100, 25, 5, 50, 425);
Button creditsButton = new Button("Credits", 60, 25, 5, 370, 425);
Button back = new Button("Home", 50, 25, 5, 700, 425);
Button settingsButton =  new Button("Settings", 75, 25, 5, 675, 425);
Settings mySettings = new Settings();


public void setup() {
  
  background(255);
  surface.setLocation(0, 0);
  questions[0] = new Question(0, 1, q1Prompts, 85, 100, "question1.JPG", "garbage.JPG");
  questions[1] = new Question(6, 2, q2Prompts, 295, 100, "question2.JPG", "solution2.jpg");
  questions[2] = new Question(3, 3, q3Prompts, 505, 100, "question3.JPG", "solution3.jpg");
  questions[3] = new Question(5, 4, q4Prompts, 180, 260, "question4.JPG", "solution4.jpg");
  questions[4] = new Question(5, 5, q5Prompts, 410, 260, "question5.JPG", "solution5.jpg");
}

public void draw() {
  //resetting canvas
  background(255);

  //if home is activated draw home screen
  if (home) {
    home();
  } 

  //if credits are activated, draw the credits screen
  else if (credits) {
    credits();
  }

  //if a question is activated show that question
  else if (showQuestion) {
    questions();
  } else if (settings) {
    mySettings.update();
  }
}


public void mousePressed() {

  //if on home page check for relevant button clicks; main question buttons
  if (home) {

    for (int i = 0; i < questions.length; i++) {

      //if the button is clicked, deactivate home page, activate question page
      if (questions[i].mainButton.hover() && home) {
        questions[i].active = true;
        input = true;
        home = false;
        showQuestion = true;
      }
    }

    //if settings is pressed while on home page
    if (settingsButton.hover()) {
      settings = true;
      home = false;
    }
  }

  //if on the credits page check for back button click
  else if (credits) {
    if (back.hover()) {
      credits = false;
      home = true;
    }
  }

  //if on the settings page pass event on to mySettings object
  else if (settings) {
    mySettings.mousePressed();
  }

  //if showing a question pass the event on to all question objects
  else {
    for (int i = 0; i < questions.length; i++) {
      questions[i].mouseClicked();
    }
  }
}

public void keyPressed() {

  //passing key press event onto all questions
  for ( int i = 0; i < questions.length; i++ ) {
    questions[i].keyPressed();
  }
}

public void mouseDragged() {
  if (settings) {
    mySettings.mouseDragged();
  }
}

public void solveQuestions() {

  //calling the solve function in the active question
  for (int i = 0; i < questions.length; i++) {
    if (questions[i].active) {
      questions[i].solve();
    }
  }
}

public void home() {

  //showing welcome message
  fill(0);
  textSize(20);
  textAlign(CENTER, TOP);
  text("Welcome to Feisty's 202 Helper!", 400, 10);
  textSize(16);
  text("Select a question below and report any bugs to @thefeistyone.", 400, 40);

  //showing mainbuttons
  for (int i = 0; i < questions.length; i++) {
    questions[i].mainButton.update();
  }

  //showing settings button
  settingsButton.update();
}

public void credits() {

  //showing credits message
  fill(0);
  textSize(30);
  textAlign(LEFT, TOP);
  text("Credits:", 10, 10);
  textSize(16);
  text("Graphical User Interface:\n-thefeistyone \nBack End:\n-thefeistyone \nSolutions:\n-thefeistyone \nApplication Bundling and Distribution:\n-thefeistyone \nSupport\n-thefeistyone \n\nI spend a lot of time developing quality software and solutions \nfor everyone as well as making myself available for support. \nIf you would like to donate to this project please message me and we can set something up. \nBest of luck in ENGG 202! :) -thefeistyone", 10, 50);

  //updating the back button
  back.update();
}

public void questions() {

  //updating all question pages
  for (int i = 0; i < questions.length; i++) {
    questions[i].update();
  }
}

//resets all booleans. No active question, first input textfield active
public void superReset() {
  for (int i = 0; i < questions.length; i++) {
    questions[i].reset();
    input = false;
    output = false;
    showSteps = false;
    showQuestion = false;
    credits = false;
    home = false;
    settings = false;
    hideSolutions();
    mySettings.MaxY.currentVal = 500;
  }
}

//resizing the page and moving home and credits buttons back for exiting the solutions page
public void hideSolutions() {
  surface.setSize(800, 480);
  creditsButton.x = 370;
  creditsButton.y = 425;
  back.x = 700;
  back.y = 425;
}

//resizing the page and moving home and credits buttons for the solutions page
public void showSolutions() {
  int currentQuestion = 0;
  for (int i = 1; i < questions.length; i++) {
    if (questions[i].active) {
      currentQuestion = i;
    }
  }
  int w = (questions[currentQuestion].mySteps.solution.width) + 20;
  surface.setSize(w, maxY);
  creditsButton.x = w - 130;
  creditsButton.y = 50;
  back.x = w - 60;
  back.y = 50;
}

public String question1(double [] input) {

  String message = "The questions are randomized, please pay careful \n"
    +"attention to which one you are selecting!\n"
    +"The following link is to the google drive developed \n"
    +"and maintained by, you the community.\n" 
    +"As such I can not guarrantee the accuracy of this page.";

  return message;
}

public String question2(double [] input) {
  double a1 = input[0];
  double a2 = input[1];
  double a3 = input[2];
  double f1 = input[3];
  double f2 = input[4];
  double m = input[5];

  double ab = ( f1 * (a1 + a2 + a3 ) + f2 * a3 - m ) / ( 2 * a1 + a2 + a3 );
  double bc = ab - f1;
  double cd = bc;
  double de = cd - f2;
  double mb = ab * a1;
  double md = m + ab * (2 * a1 + a2) - f1 * ( a1 + a2 );
  double mcl = ab * 2 * a1 - f1 * a1;
  double mcr = mcl + m;
  double [] moments = {mb, mcr, mcl, cd};
  double mMax = max(moments);
  double mMin = min(moments);

  String message = "The answers for an a value of: " + a1 + "m, an a2 value of: " + a2 + "m, \n"
    +"an a3 value of: " + a3 + "m an F1 value of: " + f1 + "N, \n"
    +"an F2 value of: " + f2 + "N, and an M value of: " + m + "Nm are as follows:\n"
    +"\n"
    +"The shear force in section AB is: " + myRound(0, ab) + "N.\n"
    +"The shear force in section BC is: " + myRound(0, bc) + "N.\n"
    +"The shear force in section CD is: " + myRound(0, cd) + "N.\n"
    +"The shear force in section DE is: " + myRound(0, de) + "N.\n"
    +"The bending moment at B is: " + myRound(0, mb) + "Nm.\n"
    +"The bending moment at D is: " + myRound(0, md) + "Nm.\n"
    +"The maximum bending moment in the beam is: " + myRound(0, mMax) + "Nm.\n"
    +"The minimum bending moment in the beam is: " + myRound(0, mMin) + "Nm.";

  return message;
}

public String question3(double [] input) {
  double l = (double) input[0];
  double q = (double) input[1];
  double w = (double) input[2];

  double av = 0.5f * w * l - q * l;
  double ma = ( l * l ) * ( ( q  * 0.5f ) - ( w / 3 ) );
  double c0 = av;
  double c1 = q;
  double c2 = -w / ( 2 * l );
  double k0 = ma;
  double k1 = av;
  double k2 = q / 2;
  double k3 = -w / ( 6 * l );
  double x = ( l * q ) / ( w );
  double vMax = c0 + c1 * x + c2 * x * x;

  String message = "The answers for a length of: " + l + "m, a q value of: " + q + "N/m \nand a w value of: " + w + "N/m are as follows:\n"
    +"\n"
    +"The vertical reaction force at A is: " + myRound(1, av) + "N.\n."
    +"The reaction moment at A is: " + myRound(1, ma) + "Nm.\n"
    +"The coefficient c0 is: " + myRound(1, c0) + "N.\n"
    +"The coefficient c1 is: " + myRound(1, c1) + "N/m.\n"
    +"The coefficient c2 is: " + myRound(1, c2) + "N/m^2.\n"
    +"The coefficient k0 is: " + myRound(1, k0) + "Nm.\n"
    +"The coefficient k1 is: " + myRound(1, k1) + "N.\n"
    +"The coefficient k2 is: " + myRound(1, k2) + "N/m.\n"
    +"The coefficient k3 is: " + myRound(1, k3) + "N/m^2.\n"
    +"The location of maximum shear force is: " + myRound(1, x) + "m from A.\n"
    +"The maximum shear force in the beam is " + myRound(1, vMax) + "N.";

  return message;
}

public String question4(double [] input) {
  double a = input[0];
  double b = input[1];
  double f = input[2];
  double m = input[3];
  double w = input[4];

  double av = ( f * ( 2 * b + a ) - m + w * a * a * 0.5f ) / ( 2 * ( a + b ) );
  double bv = f + w * a - av;
  double mb = av * a;
  double mcl = av * ( a + b ) - f * b;
  double mcr = mcl + m;
  double md = av * ( a + 2 * b ) - f * 2 * b + m;
  double [] moments = {mb, mcl, mcr, md};
  double mMax = max(moments);


  String message = "The answers for an a value of: " + a + "m, a b value of: " + b + "m, \n"
    +"an F value of: " + f + "kN, an M value of: " + m + "kNm, \n"
    +"and a w value of: " + w + "kN/m are as follows:\n"
    +"\n"
    +"The magnitude of the vertical reaction force at A is: " + abs(myRound(1, av)) + "kN.\n"
    +"The magnitude of the vertical reaction force at B is: " + abs(myRound(1, bv)) + "kN.\n"
    +"The maximum bending moment in the beam is: " + abs(myRound(2, mMax)) + "kN.";

  return message;
}

public String question5(double [] input) {
  double a = input[0];
  double b = input[1];
  double w = input[2];
  double f = input[3];
  double m = input[4];

  double bv = ( m + w * a * a * 2 + f * ( 2 * a + b ) ) / ( 2 * a );
  double av = f + w * a * 2 - bv;
  double x = av / w;
  double mMaxA = av * x - w * x * x * 0.5f;
  double mal = - w * a * a * 0.5f + av * a;
  double mar = mal + m;
  double mb = m + av * 2 * a - w * 2 * a * a;
  double [] moments = {mMaxA, mal, mar, mb, 0};
  double mMin = min(moments);
  double mMax = max(moments);

  String message = "The answers for an a value of: " + a + "m, a b value of: " + b + "m, \n"
    +"a w value of: " + w + "kN/m, an F value of: " + f + "kN\n"
    +"and an M value of: " + m + "kNm are as follows:\n"
    +"\n"
    +"The magnitude of the vertical reaction force at A is: " + abs(myRound(1, av)) + "kN.\n"
    +"The magnitude of the vertical reaction force at B is: " + abs(myRound(1, bv)) + "kN.\n"
    +"The location where the shear force is zero between 0 < x < a is: " + myRound(2, x) + "m.\n"
    +"The maximum bending moment between 0 < x < a is: " + myRound(1, mMaxA) + "kNm.\n"
    +"The maximum bending moment in the beam is: " + myRound(1, mMax) + "kNm\n"
    +"The minimum bending moment in the beam is: " + myRound(1, mMin) + "kNm";

  return message;
}

public double max(double [] lst) {
  double max = lst[0];
  for(int i = 0; i < lst.length; i++) {
    if(lst[i] > max) {
      max = lst[i];
    }
  }
  return max;
}

public double min(double[] lst) {
  double min = lst[0];
  for(int i = 0; i < lst.length; i++) {
    if(lst[i] < min) {
      min = lst[i];
    }
  }
  return min;
}

public double abs(double n) {
  if(n <= 0) {
    return -n;
  }
  else {
    return n;
  }
}

public double myRound(int decimals, double n) {
  boolean negative = false;
  if (n < 0) {
    negative = true;
    n *= -1;
  } else if (n == 0) {
    return 0;
  }
  for (int i = 0; i < decimals; i++) {
    n *= 10;
  }
  n = round((float)n);
  for (int i = 0; i < decimals; i++) {
    n /= 10;
  }
  if (negative) {
    return -n;
  } else {
    return n;
  }
}
class Button {
  String name = "";
  float wide = 100;
  float high = 100;
  float borderRadius = 0;
  float x = 0;
  float y = 0;
  boolean state = false;
  int inActive = color(23, 20, 184);
  int active = color(86, 196, 227);
  int hover = color(30, 184, 200);
  int labelColor = 255;
  Button(String nameIn, float widthIn, float heightIn, float borderIn, float xIn, float yIn) {
    name = nameIn;
    wide = widthIn;
    high = heightIn;
    borderRadius = borderIn;
    x = xIn;
    y = yIn;
  }

  public boolean hover() {
    if (mouseX >= x && mouseX <= x + wide && mouseY >= y && mouseY <= high + y) {
      return true;
    } else {
      return false;
    }
  }
  public void update() {
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
class ImageButton {
  String name = "";
  float wide = 100;
  float high = 100;
  float borderRadius = 0;
  float x = 0;
  float y = 0;
  boolean state = false;
  int inActive = color(23, 20, 184);
  int active = color(86, 196, 227);
  int hover = color(30, 184, 200);
  int labelColor = 0;
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
  public void setRadius(float r) {
    borderRadius = r;
  }
  public void setLabelColor(int labelColorIn) {
    labelColor = labelColorIn;
  }
  public void setLabel(String nameIn) {
    name = nameIn;
  }
  public void setInActiveColor(int inActiveIn) {
    inActive = inActiveIn;
  }
  public void setActiveColor(int activeIn) {
    active = activeIn;
  }
  public void setHoverColor(int hoverIn) {
    hover = hoverIn;
  }
  public void setSize(float widthIn, float heightIn) {
    wide = widthIn;
    high = heightIn;
  }
  public void setPosition(float xIn, float yIn) {
    x = xIn;
    y = yIn;
  }
  public boolean hover() {
    if (mouseX >= x && mouseX <= x + wide && mouseY >= y && mouseY <= high + y) {
      return true;
    } else {
      return false;
    }
  }
  public void update() {
    textSize(18);
    noStroke();
    fill(255);
    if (state) {
      rect(x, y, wide, high, borderRadius);
      image(background, x, y, background.width, background.height);
    } else if (!state && this.hover()) {
      rect(x, y, wide, high, borderRadius);
      image(background, x - background.width * 0.05f, y - background.height * 0.05f, background.width * 1.1f, background.height * 1.1f);
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

  public void update() {

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

  public void keyPressed() {

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

  public void mouseClicked() {

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

  public void reset() {
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
class Output {
  Button link;

  String message = "";

  Output() {
    link = new Button("Drive", 100, 25, 5, 10, 200);
  }

  public void update() {

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

  public void mouseClicked() {

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

  public void update() {

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

  public void mouseClicked() {

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

  public void keyPressed() {

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

  public void getInput() {
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

  public void solve() {
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

  public void showQNum() {
    fill(0);
    textSize(30);
    textAlign(LEFT, TOP);
    text("Question " + questionNum + ":", 10, 10);
  }

  public void reset() {
    active = false;
    myInput.reset();
    data = new double[myInput.inputs.length];
  }
}
class Settings {
  Slider MaxY;
  Button cancel;
  Button apply;

  Settings() {
    MaxY = new Slider(50, 130, 200, 20, 700, 1100);
    cancel = new Button("Cancel", 50, 25, 5, 50, 425);
    apply = new Button("Apply", 50, 25, 5, 375, 425);
  }

  public void update() {
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

  public void mousePressed() {
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

  public void mouseDragged() {
    MaxY.mouseDragged();
  }
}
class Slider {
  float x;
  float y;
  float w;
  float h;
  float min;
  float max;
  int inActive = color(23, 20, 184);
  int active = color(30, 184, 200);
  int backgroundColor = color (255);
  int labelColor = color(0);
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

  public boolean hover() {
    if (mouseX >= slideX && mouseX <= slideX + slideWidth && mouseY >= y - 10 && mouseY < y + h + 10) {
      return true;
    } else {
      return false;
    }
  }

  public void mouseDragged() {
    if (activeBool) {
      if (mouseX - offSet < x + w) {
        slideX = mouseX - offSet;
      }
    }
  }
  
  public void mousePressed() {
    if(hover()) {
      this.activeBool = true;
      offSet = mouseX - slideX;
    }
  }

  public void update() {
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
class Steps {
  PImage solution;
  Steps(String steps) {
    solution = loadImage(steps);
    solution.resize(0, maxY - 60);
  }

  public void update() {
    textAlign(LEFT, TOP);
    textSize(30);
    fill(0);
    text("Example Solution:", 200, 10);
    image(solution, 10, 50);

    back.update();
    creditsButton.update();
  }

  public void mouseClicked() {
    if (back.hover()) {
      superReset();
      home = true;
    } else if (creditsButton.hover()) {
      superReset();
      credits = true;
    }
  }
}
class TextField {
  float w, x, y, h = 20;
  int background = color(214, 210, 210);
  boolean state = false;
  String value = "";

  TextField(float wIn, float xIn, float yIn) {
    w = wIn;
    x = xIn;
    y = yIn;
  }

  public boolean hover() {
    if (mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= h + y) {
      return true;
    } else {
      return false;
    }
  }

  public void mouseClicked() {
    //if the textfield is clicked on it will become active
    if (hover()) {
      state = true;
    }
    //if anywhere else if clicked it will become inactive
    else {
      state = false;
    }
  }

  public void keyPressed() {

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

  public void clear() {
    value = "";
  }

  public void update() {
    textAlign(LEFT, TOP);
    fill(background);
    textSize(15);
    if (state) {
      stroke(0);
      rect(x, y, w, h, 5);
      fill(0);
      text(value, x + 5, y + 1.5f);
      //covering overflow
      fill(255);
      noStroke();
      rect(x + w + 1, y, 400, h);
    } else {
      fill(background);
      noStroke();
      rect(x, y, w, h, 5);
      fill(0);
      text(value, x + 5, y + 1.5f);
      //covering overflow
      fill(255);
      noStroke();
      rect(x + w + 1, y, 400, h);
    }
  }
}
  public void settings() {  size(800, 500); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Assignment10" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
