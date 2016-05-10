import processing.svg.*;
import gab.opencv.*;
import processing.video.*;
import java.awt.*;

Capture video;
OpenCV opencv;
PImage character, logo, banner, bg;
String imgname;
boolean swapped = false;
boolean drifted = false;
int facey, facex, timer, prevTime, margin;
int interval = 12000;
PShape cloud1, cloud2, cloud3, cloud4;

void setup() {
  fullScreen();
  noFill();
  frameRate(45);
  background(145,213,241);
  bg = loadImage("suchclouds@1280.png");
  video = new Capture(this, 1280, 720);
  opencv = new OpenCV(this, 1280, 720);
  opencv.loadCascade(OpenCV.CASCADE_FRONTALFACE);  
  logo = loadImage("CoasterLogo.png");
  margin = (displayWidth - 1280) / 2;
  banner = loadImage("bannerplaceholder.png");
  video.start();
}

void draw() {
  if (displayWidth == 1280) {
    background(bg);
  }
  else {    
    background(145,213,241);
  }
  scale(0.8);
  timer = millis();
  opencv.loadImage(video);
  image(logo, 10, 10);
  image(banner, 0, displayHeight - 238);
  image(video, margin, 100);
  
  // set the timer off and snap a screenshot 
  // plus restart some cloud animations here
  if (timer - prevTime >= interval) {
    prevTime = timer;
    // # char is directive to name files incrementally (0001, 0002, etc)
    saveFrame("output/manifest####.png");
  }

  Rectangle[] faces = opencv.detect();

  //Outline face:
  fill(39, 170, 225);
  //POSITION SQUARE
  //a  float: x-coordinate of the rectangle by default
  //b  float: y-coordinate of the rectangle by default
  //c  float: width of the rectangle by default
  //d  float: height of the rectangle by default
  rect(39, 170, 225, 55);
 
  //text
  textSize(45);
  fill(255, 255, 255);
  //POSITION TEXT
  text("Come to room 149 for more!", 10, 30); 

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