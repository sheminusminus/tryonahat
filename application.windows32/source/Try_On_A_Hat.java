import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import processing.svg.*; 
import gab.opencv.*; 
import processing.video.*; 
import java.awt.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Try_On_A_Hat extends PApplet {






Capture video;
OpenCV opencv;
PImage character, logo, banner, bg;
String imgname;
boolean swapped = false;
boolean drifted = false;
int facey, facex, timer, prevTime, margin;
int interval = 12000;
PShape cloud1, cloud2, cloud3, cloud4;

public void setup() {
  
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

public void draw() {
  if (displayWidth == 1280) {
    background(bg);
  }
  else {    
    background(145,213,241);
  }
  scale(0.8f);
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


  image(character, faces[i].x + faces[i].width/2, faces[i].y*.8f, faces[i].width*1.3f, faces[i].height*.8f);
  //image(character, faces[i].x + faces[i].width, faces[i].y);
 }
 
  popStyle(); 
}

public void captureEvent(Capture c) {
  c.read();
}
  public void settings() {  fullScreen(); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#666666", "--stop-color=#cccccc", "Try_On_A_Hat" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
