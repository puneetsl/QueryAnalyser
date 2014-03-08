package edu.buffalo.queryanalyser;

import edu.buffalo.queryanalyser.reader.FindAttribute;
import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class QueryParser {
	private String query;
	private String Topic;
	private String qCategory;
	private String Attribute;
	private boolean isPhrase=false;
	private boolean isProcessed=false;
	LexicalizedParser lp;
	String Path;
	AbstractSequenceClassifier<?> classifier;
	String serializedClassifier;
	public QueryParser(String Path) {
		LoadModules lm = LoadModules.getInstance(Path);
		lp = LoadModules.getLp();
		serializedClassifier = LoadModules.getSerializedClassifier();
		classifier = LoadModules.getClassifier();
		this.Path = Path;
	}
	public void setQuery(String query)
	{
		this.query = query.toLowerCase();
	}
	public static String capitalizeString(String toBeCapped)
	{
		if(toBeCapped.equals(""))
			return "";
		String[] tokens = toBeCapped.split("\\s");
		toBeCapped = "";

		for(int i = 0; i < tokens.length; i++){
		    char capLetter = Character.toUpperCase(tokens[i].charAt(0));
		    toBeCapped +=  " " + capLetter + tokens[i].substring(1, tokens[i].length());
		}
		return toBeCapped;
	}
	public int ParseQuery()
	{
		
		//query = QueryCleanAndParse.removeBekaarWords(query);
		Topic = TopicDetect.getTopic(lp, QueryCleanAndParse.clean(query)).toLowerCase();
		qCategory = QuestionCategoryDetect.getCategory(lp, QueryCleanAndParse.clean(query)).toLowerCase();
		Attribute = AttributeDetect.getAttribute(lp, QueryCleanAndParse.clean(query)).toLowerCase();
		if(query.split("'").length>1)
		{
			Topic = query.substring(query.indexOf("'")+1, query.indexOf("'",query.indexOf("'")+1));
			isPhrase = true;
			return 0;
		}
		//System.out.println("Topic: "+Topic);
		String[] chkOf = Topic.split(" of ");
		if(chkOf.length>1)
		{
			Topic = chkOf[1].trim();
			Attribute = chkOf[0].trim();
		}

		if(Attribute.equals(" of "))
		{
			chkOf = query.split("of");
			Attribute = chkOf[0].split("\\s")[chkOf[0].split("\\s").length - 1];
			Topic = chkOf[1].trim();
		}

		if(Attribute.equals(""))
		{
			Attribute = "@@@";
		}
		Topic = capitalizeString(Topic.toLowerCase());
		String tempNER = classifier.classifyWithInlineXML(Topic);
		String NER="";
		if(tempNER.contains("<"))
		 NER = tempNER.substring(tempNER.indexOf("<")+1, tempNER.indexOf(">")).trim().toLowerCase();
		String tempWiki = FindAttribute.getAttribute(qCategory, NER, Attribute,Path);
		if(!tempWiki.trim().equals(""))
		{
			Attribute = tempWiki;
			isProcessed = true;
		}
		if(Topic.trim().equals(""))
		{
			Topic = query;
		}
		return 0;
	}
	public String getQuery() {
		return query;
	}
	public String getTopic() {
		return Topic;
	}
	public String getqCategory() {
		return qCategory;
	}
	public String getAttribute() {
		return Attribute;
	}
	public boolean isPhrase() {
		return isPhrase;
	}
	public boolean isProcessed() {
		return isProcessed;
	}
}
