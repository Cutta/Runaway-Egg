package com.example.andengine;

public class Level {
private boolean firstStar;
private boolean secondStar;
private boolean thirdStar;

Level(){
	
	firstStar = secondStar = thirdStar = false;
	
}

void setFirstStar(boolean b){  firstStar = b;   }
boolean getFirstStar(){  return firstStar; }

void setSecondStar(boolean b){  secondStar = b;   }
boolean getSecondStar(){  return secondStar; }

void setThirdStar(boolean b){  thirdStar = b;   }
boolean getThirdStar(){  return thirdStar; }

}
