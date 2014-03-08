package edu.buffalo.queryanalyser.reader;

import java.util.ArrayList;
import java.util.HashMap;

public class MapBean {
	//Entities to question type
	public HashMap<String, HashMap<String,ArrayList<Mappings>>> entity = new HashMap<String, HashMap<String,ArrayList<Mappings>>>();
}
class Mappings{
	public ArrayList<String> words = new ArrayList<String>();
	public String map="";
}
