package edu.buffalo.queryanalyser.reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SlurpMappings {
	MapBean mb = new MapBean();

	public MapBean readFile(String Path) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(Path+"/properties/mapping.txt"));
		String line;
		while ((line = br.readLine()) != null) {
			if(line.startsWith("#"))
				continue;
			else
			{
				String[] spltVals = line.split(";");
				String[] words = spltVals[2].split(",");
				Mappings m = new Mappings();
				for (int i = 0; i < words.length; i++) {
					m.words.add(words[i]);
				}
				m.map = spltVals[3];
				if(mb.entity.containsKey(spltVals[1]))
				{
					if(mb.entity.get(spltVals[1]).containsKey(spltVals[0]))
					{
						mb.entity.get(spltVals[1]).get(spltVals[0]).add(m);
					}
					else
					{
						ArrayList<Mappings> mapping = new ArrayList<Mappings>();
						mb.entity.get(spltVals[1]).put(spltVals[0], mapping);
						mb.entity.get(spltVals[1]).get(spltVals[0]).add(m);
					}
				}
				else
				{
					HashMap<String,ArrayList<Mappings>> questionType = new HashMap<String, ArrayList<Mappings>>();
					mb.entity.put(spltVals[1], questionType);
					ArrayList<Mappings> mapping = new ArrayList<Mappings>();
					mb.entity.get(spltVals[1]).put(spltVals[0], mapping);
					mb.entity.get(spltVals[1]).get(spltVals[0]).add(m);
					
				}
			}
		}
		br.close();
		return mb;
	}
}