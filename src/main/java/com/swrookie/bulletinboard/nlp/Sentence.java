package com.swrookie.bulletinboard.nlp;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Sentence 
{
	private int paragraphNumber;	// number indicating which paragraph sentence belongs to
	private int number;				// senetence number respect to entire text
	private int stringLength;		// Don't need this
	private double score;			// score indicating importance
	private int numWords;
	private String value;
	
	public Sentence(int paragraphNumber, int number, int stringLength, String value)
	{
		this.paragraphNumber = paragraphNumber;
		this.number = number;
		this.value = new String(value);
		this.stringLength = stringLength;
		this.numWords = value.split("\\s+").length;
		score = 0.0;
	}
}
