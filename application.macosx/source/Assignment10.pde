import javax.swing.JOptionPane;
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


void setup() {
  size(800, 500);
  background(255);
  surface.setLocation(0, 0);
  questions[0] = new Question(0, 1, q1Prompts, 85, 100, "question1.JPG", "garbage.JPG");
  questions[1] = new Question(6, 2, q2Prompts, 295, 100, "question2.JPG", "solution2.jpg");
  questions[2] = new Question(3, 3, q3Prompts, 505, 100, "question3.JPG", "solution3.jpg");
  questions[3] = new Question(5, 4, q4Prompts, 180, 260, "question4.JPG", "solution4.jpg");
  questions[4] = new Question(5, 5, q5Prompts, 410, 260, "question5.JPG", "solution5.jpg");
}

void draw() {
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


void mousePressed() {

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

void keyPressed() {

  //passing key press event onto all questions
  for ( int i = 0; i < questions.length; i++ ) {
    questions[i].keyPressed();
  }
}

void mouseDragged() {
  if (settings) {
    mySettings.mouseDragged();
  }
}

void solveQuestions() {

  //calling the solve function in the active question
  for (int i = 0; i < questions.length; i++) {
    if (questions[i].active) {
      questions[i].solve();
    }
  }
}

void home() {

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

void credits() {

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

void questions() {

  //updating all question pages
  for (int i = 0; i < questions.length; i++) {
    questions[i].update();
  }
}

//resets all booleans. No active question, first input textfield active
void superReset() {
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
void hideSolutions() {
  surface.setSize(800, 480);
  creditsButton.x = 370;
  creditsButton.y = 425;
  back.x = 700;
  back.y = 425;
}

//resizing the page and moving home and credits buttons for the solutions page
void showSolutions() {
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

String question1(double [] input) {

  String message = "The questions are randomized, please pay careful \n"
    +"attention to which one you are selecting!\n"
    +"The following link is to the google drive developed \n"
    +"and maintained by, you the community.\n" 
    +"As such I can not guarrantee the accuracy of this page.";

  return message;
}

String question2(double [] input) {
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

String question3(double [] input) {
  double l = (double) input[0];
  double q = (double) input[1];
  double w = (double) input[2];

  double av = 0.5 * w * l - q * l;
  double ma = ( l * l ) * ( ( q  * 0.5 ) - ( w / 3 ) );
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

String question4(double [] input) {
  double a = input[0];
  double b = input[1];
  double f = input[2];
  double m = input[3];
  double w = input[4];

  double av = ( f * ( 2 * b + a ) - m + w * a * a * 0.5 ) / ( 2 * ( a + b ) );
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

String question5(double [] input) {
  double a = input[0];
  double b = input[1];
  double w = input[2];
  double f = input[3];
  double m = input[4];

  double bv = ( m + w * a * a * 2 + f * ( 2 * a + b ) ) / ( 2 * a );
  double av = f + w * a * 2 - bv;
  double x = av / w;
  double mMaxA = av * x - w * x * x * 0.5;
  double mal = - w * a * a * 0.5 + av * a;
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

double max(double [] lst) {
  double max = lst[0];
  for(int i = 0; i < lst.length; i++) {
    if(lst[i] > max) {
      max = lst[i];
    }
  }
  return max;
}

double min(double[] lst) {
  double min = lst[0];
  for(int i = 0; i < lst.length; i++) {
    if(lst[i] < min) {
      min = lst[i];
    }
  }
  return min;
}

double abs(double n) {
  if(n <= 0) {
    return -n;
  }
  else {
    return n;
  }
}

double myRound(int decimals, double n) {
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
