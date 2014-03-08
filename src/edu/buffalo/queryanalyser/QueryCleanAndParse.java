package edu.buffalo.queryanalyser;

public class QueryCleanAndParse {
	public static String removeBekaarWords(String text) {

		String[] query = text.split("\\s");
		StringBuilder sb = new StringBuilder();
		String[] stopwords = {"is","was","the","a","and","has","have","had","in","into"};//expand list
		for (int i = 0; i < query.length; i++) {
			//iterate through all stopwords
			B:for (int j = 0; j < stopwords.length; j++) {
				if (query[i].toLowerCase().equals(stopwords[j])) { 
					break B;
				} 
				else { 
					// if this is the last stopword print it
					// it means query[i] does not equals with all stopwords
					if(j==stopwords.length-1)
					{
						sb.append(query[i]+" ");
					}
				}
			}
		} 
		return sb.toString().trim();
	}
	public static String clean(String text)
	{
//		String temptext = text.replaceAll("['!@#%\\$\\^\\(\\)\\~\\?]|'s", "");
//		temptext = temptext.replaceAll("'s", "");
		return text.replaceAll("['’][s]|['!@#%\\$\\^\\(\\)\\~\\?]", "");
	}
}
