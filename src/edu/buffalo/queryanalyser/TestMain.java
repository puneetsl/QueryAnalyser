package edu.buffalo.queryanalyser;

import java.text.SimpleDateFormat;
import java.util.Date;

import edu.stanford.nlp.ling.tokensregex.parser.ParseException;

public class TestMain {
	public static void main(String[] args) {
		String s = "1946-12-09";
		if(s.matches("[0-9][0-9][0-9][0-9]-[0-9][0-9]-[0-9][0-9]"))
		{
			System.out.println("yes!!");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-mm-dd");
			SimpleDateFormat formatter2 = new SimpleDateFormat("DD, MMMM, yyyy");
			try {
				Date date = formatter.parse(s);
				System.out.println(formatter2.format(date));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else
		{
			System.out.println("damn!");
		}
	}
}
