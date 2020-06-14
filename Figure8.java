package com;
import java.util.ArrayList;
import java.io.Serializable;
public class Figure8 implements Serializable{
	String hashtag;
	String annotator;
	ArrayList<String> annotate = new ArrayList<String>();
public void setHashtag(String hashtag){
	this.hashtag = hashtag;
}
public String getHashtag(){
	return hashtag;
}

public void setAnnotator(String annotator){
	this.annotator = annotator;
}
public String getAnnotator(){
	return annotator;
}

public void setAnnotate(String tag){
	annotate.add(tag);
}
public ArrayList<String> getAnnotate(){
	return annotate;
}
}