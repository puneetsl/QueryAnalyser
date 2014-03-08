package edu.buffalo.queryanalyser.reader;

import java.io.IOException;
import java.util.ArrayList;

import uk.ac.shef.wit.simmetrics.similaritymetrics.QGramsDistance;

public class FindAttribute {
	static QGramsDistance ds =  new QGramsDistance();
	static MapBean mb = new MapBean();
	public FindAttribute() {

	}
	
	public static String getAttribute(String qType,	String ner,	String attribute, String Path)
	{
		SlurpMappings sm = new SlurpMappings();
		
		try {
			if(mb.entity.isEmpty())
				mb = sm.readFile(Path);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(mb.entity.containsKey(ner))
		{
			if(mb.entity.get(ner).containsKey(qType))
			{
				ArrayList<Mappings> maps =mb.entity.get(ner).get(qType);
				for (int i = 0; i < maps.size(); i++) {
					ArrayList<String> words = maps.get(i).words;
					for (int j = 0; j < words.size(); j++) {
						
						double d =ds.getSimilarity(attribute, words.get(j));
						if(d>0.95)
						{
							return maps.get(i).map;
						}
						if(attribute.contains(words.get(j)))
						{
							return maps.get(i).map;
						}
					}
				}
			}
		}
		ner = "empty";
		if(mb.entity.containsKey(ner))
		{
			if(mb.entity.get(ner).containsKey(qType))
			{
				ArrayList<Mappings> maps =mb.entity.get(ner).get(qType);
				for (int i = 0; i < maps.size(); i++) {
					ArrayList<String> words = maps.get(i).words;
					for (int j = 0; j < words.size(); j++) {
						double d =ds.getSimilarity(attribute, words.get(j));
						//System.out.println(attribute+" : "+ words.get(j)+" : "+d);
						if(d>0.5)
						{
							return maps.get(i).map;
						}
						if(attribute.contains(words.get(j)))
						{
							return maps.get(i).map;
						}
					}
				}
			}
		}
	return "";
	}
}
