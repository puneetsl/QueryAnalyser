package edu.buffalo.queryanalyser;

import edu.stanford.nlp.ie.AbstractSequenceClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.parser.lexparser.LexicalizedParser;

public class LoadModules {
	private static  LoadModules instance = null;
	private static LexicalizedParser lp=null;
	private static AbstractSequenceClassifier<?> classifier=null;
	private static String serializedClassifier=null;
	private LoadModules()
	{
		
	}
	public static LoadModules getInstance(String Path)
	{
		if(instance == null)
		{
			instance = new LoadModules();
			instance.lp = LexicalizedParser.loadModel("edu/stanford/nlp/models/lexparser/englishPCFG.ser.gz");
			instance.serializedClassifier = Path+"/classifiers/english.conll.4class.distsim.crf.ser.gz";
			instance.classifier = CRFClassifier.getClassifierNoExceptions(serializedClassifier);
		}
		
		return instance;
	}
	public static LexicalizedParser getLp() {
		return instance.lp;
	}
	public static AbstractSequenceClassifier<?> getClassifier() {
		return instance.classifier;
	}
	public static String getSerializedClassifier() {
		return instance.serializedClassifier;
	}
}
