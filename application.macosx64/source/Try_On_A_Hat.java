import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

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
PImage character, logo, bg;
//PImage banner;
String imgname;
boolean swapped = false;
boolean drifted = false;
int facey, facex, timer, prevTime;
int interval = 12000;
int textline = 60;
float marginW, marginH;

public void setup() {
  
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

public void draw() {
  if (this.width == 1280) {
    background(bg);
  }
  else {    
    background(145,213,241);
  }
  imageMode(CENTER);
  timer = millis();
  scale(0.8f);
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
  text("Come to room 149 for more!", this.width * 0.3f, displayHeight + 150); 

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
