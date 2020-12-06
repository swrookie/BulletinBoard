package com.swrookie.bulletinboard.nlp;

import java.util.Comparator;

public class SentenceComparator implements Comparator<Sentence>
{
	@Override
	public int compare(Sentence obj1, Sentence obj2)
	{
		if(obj1.getScore() > obj2.getScore())
			return -1;
		else if (obj1.getScore() < obj2.getScore())
			return 1;
		else
			return 0;
	}
}
