package com.swrookie.bulletinboard.nlp;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Paragraph 
{
	private int number;
	private List<Sentence> sentences;
	
	public Paragraph(int number)
	{
		this.number = number;
		sentences = new ArrayList<Sentence>();
	}
}
