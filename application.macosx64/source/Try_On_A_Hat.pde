import gab.opencv.*;
import processing.video.*;
import java.awt.*;

Capture video;
OpenCV opencv;
PImage character, logo, bg;
//PImage banner;
String imgname;
boolean swapped = false;
boolean drifted = false;
int facey, facex, timer, prevTime;
int interval = 12000;
int textline = 60;
float marginW, marginH;

void setup() {
  fullScreen();
  frameRate(45);
  background(145,213,241);
  bg = loadImage("suchclouds@1280.png");
  video = new Capture(this, 1280, 720);
  opencv = new OpenCV(this, 1280, 720);
  opencv.loadCascade(OpenCV.CASCADE_FRONTALFACE);  
  logo = loadImage("CoasterLogo.png");
  //banner = loadImage("bannerplaceholder.png");
  video.start();
}

void draw() {
  if (this.width == 1280) {
    background(bg);
  }
  else {    
    background(145,213,241);
  }
  imageMode(CENTER);
  timer = millis();
  scale(0.8);
  opencv.loadImage(video);
  image(video, this.width/2 + 150, this.height/2 + 100);
  image(logo, logo.width/2 + 40, logo.height - 50);

  // set the timer off and snap a screenshot 
  if (timer - prevTime >= interval) {
    prevTime = timer;
    // # char is directive to name files incrementally (0001, 0002, etc)
    saveFrame("output/manifest####.png");
  }

  Rectangle[] faces = opencv.detect();
  //Outline face:
  //fill(39, 170, 225);
  noFill();
  noStroke();
  //POSITION SQUARE
  //a  float: x-coordinate of the rectangle by default
  //b  float: y-coordinate of the rectangle by default
  //c  float: width of the rectangle by default
  //d  float: height of the rectangle by default
  rect(39, 170, 225, 55);

  //text
  textSize(80);
  fill(255, 255, 255);
  //POSITION TEXT
  text("Come to room 149 for more!", this.width * 0.3, displayHeight + 150); 

  pushStyle();
  imageMode(CENTER);
   
  for (int i = 0; i < faces.length; i++) {
   
  facex = faces[i].x;
  facey = faces[i].y;
 
  imgname = i + ".png";
  character = loadImage(imgname);


  image(character, faces[i].x + faces[i].width/2, faces[i].y*.8, faces[i].width*1.3, faces[i].height*.8);
  //image(character, faces[i].x + faces[i].width, faces[i].y);
 }
  
  
  popStyle();
}

void captureEvent(Capture c) {
  c.read();
}