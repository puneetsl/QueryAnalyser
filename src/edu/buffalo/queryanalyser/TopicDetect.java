package edu.buffalo.queryanalyser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.trees.*;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class TopicDetect {
	public static List<Tree> GetProperNounPhrases(Tree parse)
	{

		List<Tree> phraseList=new ArrayList<Tree>();
		for (Tree subtree: parse)
		{

			if(subtree.label().value().equals("NNP"))
			{

				phraseList.add(subtree);
				//System.out.println(subtree);

			}
		}

		return phraseList;

	}
	public static List<Tree> GetNounPhrases(Tree parse)
	{

		List<Tree> phraseList=new ArrayList<Tree>();
		for (Tree subtree: parse)
		{

			if(subtree.label().value().equals("NP"))
			{

				phraseList.add(subtree);
				//System.out.println(subtree);

			}
		}

		return phraseList;

	}
	public static String deDup(String s) {
		//using code from here http://stackoverflow.com/questions/6790689/remove-duplicate-values-from-a-string-in-java
	    return new LinkedHashSet<String>(Arrays.asList(s.split(" "))).toString().replaceAll("(^\\[|\\]$)", "").replace(", ", " ");
	}
	public static String getTopic(LexicalizedParser lp, String query) {
		if(lp==null)
		{
			lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
		}
		String[] sent = query.split(" ");
		List<CoreLabel> rawWords = Sentence.toCoreLabelList(sent);
		Tree parse = lp.apply(rawWords);
		List<Tree> l = GetProperNounPhrases(parse);
		String Topic="";
		for (int i = 0; i < l.size(); i++) {
			for (Tree subtree: l.get(i))
			{
				if(subtree.isLeaf())
				{
					if(Topic.equals(""))
					{
						Topic = subtree.label().toString();
					}
					else
					{
						Topic = Topic +" "+subtree.label();
					}
					//System.out.println(subtree.label());
				}
			}
		}

		if(Topic.trim().equals(""))
		{
			l = GetNounPhrases(parse);
			for (int i = 0; i < l.size(); i++) {
				for (Tree subtree: l.get(i))
				{
					if(subtree.isLeaf())
					{
						if(Topic.equals(""))
						{
							Topic = subtree.label().toString();
						}
						else
						{
							Topic = Topic +" "+subtree.label();
						}
					}
				}
			}
		}
		Topic = deDup(Topic);
		Topic = QueryCleanAndParse.removeBekaarWords(Topic);
		return Topic;
	}
	/*
	 * Detection of main topic from query using POS and NER 
	 */

}
