package com.swrookie.bulletinboard.nlp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SummaryTool 
{
	private List<Sentence> sentences, contentSummary;
	private List<Paragraph> paragraphs;
	private int numSentences, numParagraphs;				// no. of sentences and paragraphs
	private double[][] intersectionMatrix;
	private LinkedHashMap<Sentence, Double> dictionary;
	
	public SummaryTool()
	{
		numSentences = 0;
		numParagraphs = 0;
	}
	
	public void init()
	{
		sentences = new ArrayList<Sentence>();
		contentSummary = new ArrayList<Sentence>();
		paragraphs = new ArrayList<Paragraph>();
		dictionary = new LinkedHashMap<Sentence, Double>();
		numSentences = 0;
		numParagraphs = 0;
	}
	
	// Retrieve sentences from the entire passage
	public void extractSentenceFromContext(String content)
	{
		int nextChar, j = 0;
		int prevChar = -1;
		
//		try
//		{
//			j = 0;
//			char[] temp = content.toCharArray();
//			while((char)nextChar != '.')
//			{
//				temp[j] = (char)nextChar;
//				if((nextChar = in.read()) == -1)
//	        		break;
//	       		if((char)nextChar == '\n' && (char)prevChar == '\n')
//	       			numParagraphs++;
//	       		
//	       		j++;
//	       		prevChar = nextChar;
//			}
//				
//			sentences.add(new Sentence(numSentences, 
//									   numParagraphs,
//									   (new String(temp)).trim().length(),
//									   (new String(temp)).trim()));
//	        numSentences++;
////	       	prevChar = nextChar;
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
	}
	
	public void groupSentencesIntoParagraphs()
	{
		int paraNum = 0;
		Paragraph paragraph = new Paragraph(0);

		for(int i=0;i<numSentences;i++){
			if(sentences.get(i).getParagraphNumber() == paraNum)
			{
				//continue
			}
			else
			{
				paragraphs.add(paragraph);
				paraNum++;
				paragraph = new Paragraph(paraNum);
				
			}
			paragraph.getSentences().add(sentences.get(i));
		}

		paragraphs.add(paragraph);
	}
	
	public double noOfCommonWords(Sentence str1, Sentence str2)
	{
		double commonCount = 0;

		for(String str1Word : str1.getValue().split("\\s+"))
		{
			for(String str2Word : str2.getValue().split("\\s+"))
			{
				if(str1Word.compareToIgnoreCase(str2Word) == 0)
					commonCount++;
			}
		}

		return commonCount;
	}

	public void createIntersectionMatrix()
	{
		intersectionMatrix = new double[numSentences][numSentences];
		for(int i = 0; i < numSentences; i++)
		{
			for(int j = 0; j < numSentences; j++)
			{
				if(i <= j)
				{
					Sentence str1 = sentences.get(i);
					Sentence str2 = sentences.get(j);
					intersectionMatrix[i][j] = noOfCommonWords(str1,str2) / ((double)(str1.getNumWords() + str2.getNumWords()) /2);
				}
				else
				{
					intersectionMatrix[i][j] = intersectionMatrix[j][i];
				}
				
			}
		}
	}

	public void createDictionary()
	{
		for(int i = 0; i < numSentences; i++)
		{
			double score = 0;
			for(int j = 0; j < numSentences; j++)
			{
				score += intersectionMatrix[i][j];
			}
			dictionary.put(sentences.get(i), score);
			((Sentence)sentences.get(i)).setScore(score);
		}
	}

	public void createSummary()
	{

	      for(int j = 0; j <= numParagraphs; j++)
	      {
	      		int primary_set = paragraphs.get(j).getSentences().size() / 5; 

	      		//Sort based on score (importance)
	      		Collections.sort(paragraphs.get(j).getSentences(), new SentenceComparator());
		      	for(int i=0;i<=primary_set;i++)
		      	{
		      		contentSummary.add(paragraphs.get(j).getSentences().get(i));
		      	}
	      }

	      //To ensure proper ordering
	      Collections.sort(contentSummary,new SentenceComparatorForSummary());
	}


	public void printSentences()
	{
		for(Sentence sentence : sentences)
		{
			System.out.println(sentence.getNumber() + " => " + 
							   sentence.getValue() + " => " + 
							   sentence.getValue()  + " => " + 
							   sentence.getNumWords() + " => " + 
							   sentence.getParagraphNumber());
		}
	}

	public void printIntersectionMatrix()
	{
		for(int i = 0; i < numSentences; i++)
		{
			for(int j = 0; j < numSentences; j++)
			{
				System.out.print(intersectionMatrix[i][j] + "    ");
			}
			System.out.print("\n");
		}
	}

	public void printDicationary()
	{
		  // Get a set of the entries
	      Set set = dictionary.entrySet();
	      // Get an iterator
	      Iterator i = set.iterator();
	      // Display elements
	      while(i.hasNext()) 
	      {
	         Map.Entry me = (Map.Entry)i.next();
	         System.out.print(((Sentence)me.getKey()).getValue() + ": ");
	         System.out.println(me.getValue());
	      }
	}

	public void printSummary()
	{
		System.out.println("no of paragraphs = "+ numParagraphs);
		for(Sentence sentence : contentSummary)
		{
			System.out.println(sentence.getValue());
		}
	}

	public double getWordCount(List<Sentence> sentenceList)
	{
		double wordCount = 0.0;
		for(Sentence sentence:sentenceList)
		{
			wordCount += (sentence.getValue().split(" ")).length;
		}
		return wordCount;
	}

	public void printStats()
	{
		System.out.println("number of words in Context : " + this.getWordCount(sentences));
		System.out.println("number of words in Summary : " + this.getWordCount(contentSummary));
		System.out.println("Commpression : " + this.getWordCount(contentSummary)/ this.getWordCount(sentences));
	}

}
