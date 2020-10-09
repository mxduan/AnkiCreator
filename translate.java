package translate;

import java.util.*;
import java.lang.*;
import java.io.*;
import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner; // Import the Scanner class to read text files
import java.io.PrintWriter;
import java.util.Map;
import java.lang.StringBuilder;
import static java.util.Map.entry;

public class translate {
	enum CardTypes {
		VOCAB,
		ENTER,
		CLOZER,
		INFO
	}

	private static void moveToFirstCard(Scanner scan) {
		while (scan.hasNextLine()) {
			String data = scan.nextLine();
			if (data.contains("START_ANKI")) {
				return;
			}
		}
		//throw RuntimeException("Start never found");
	}

	private static void translateToVocab(Scanner scan) {

	}

    public static void main (String[] args) throws java.lang.Exception
    {
      // your code goes here
      System.out.println("Vocab test successful? " + VocabCard.verifyCode());
      System.out.println("Clozer test successful? " + ClozerCard.verifyCode());
      System.out.println("Info test successful? " + InfoCard.verifyCode());



      File myObj = new File("notes.md");
      Scanner myReader = new Scanner(myObj);
      PrintWriter writerVocab = new PrintWriter("vocab_cards.txt", "UTF-8");
      PrintWriter writerEnter = new PrintWriter("enter_cards.txt", "UTF-8");
      PrintWriter writerClozer = new PrintWriter("clozer_cards.txt", "UTF-8");
      PrintWriter writerInfo = new PrintWriter("info_cards.txt", "UTF-8");

      moveToFirstCard(myReader);
      int cardCount = 0;
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        if (data.toLowerCase().equals("vocab")) {
        	VocabCard card = new VocabCard(myReader);
        	card.translateToCard();
        	card.printToFile(writerVocab);
          cardCount++;
        } else if (data.toLowerCase().equals("enter")) {
        	VocabCard card = new VocabCard(myReader);
        	card.translateToCard();
        	card.printToFile(writerEnter);
          cardCount++;
        } else if (data.toLowerCase().equals("clozer")) {
        	ClozerCard card = new ClozerCard(myReader);
        	card.translateToCard();
        	card.printToFile(writerClozer);
          cardCount++;
        } else if (data.toLowerCase().equals("info")) {
        	InfoCard card = new InfoCard(myReader);
        	card.translateToCard();
        	card.printToFile(writerInfo);
          cardCount++;
        } else if(data.equals("NO_ANKI")) {
          break;
        } else if(data.equals("---")) {
          continue;
        }
      }
      System.out.println("Total cards created: " + cardCount);
      myReader.close();
      writerVocab.close();
      writerEnter.close();
      writerClozer.close();
      writerInfo.close();
    }
}
