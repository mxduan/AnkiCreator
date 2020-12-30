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

	private static void moveToFirstCard(PeekableScanner scan) {
		while (scan.hasNextLine()) {
			String data = scan.nextLine();
			if (data.contains("START_ANKI")) {
				return;
			}
		}
		//throw RuntimeException("Start never found");
	}

  public static void main (String[] args) throws java.lang.Exception
  {
    System.out.println("Vocab test successful? " + CardUtils.verify("VocabCard"));
    System.out.println("Clozer test successful? " + CardUtils.verify("ClozerCard"));
    System.out.println("Info test successful? " + CardUtils.verify("InfoCard"));

    PeekableScanner myReader = new PeekableScanner("notes.md");
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
        writerVocab.write(CardUtils.getString(card));
        cardCount++;
      } else if (data.toLowerCase().equals("enter")) {
      	VocabCard card = new VocabCard(myReader);
      	card.translateToCard();
        writerEnter.write(CardUtils.getString(card));
        cardCount++;
      } else if (data.toLowerCase().equals("clozer")) {
      	ClozerCard card = new ClozerCard(myReader);
      	card.translateToCard();
      	writerClozer.write(CardUtils.getString(card));
        cardCount++;
      } else if (data.toLowerCase().equals("info")) {
      	InfoCard card = new InfoCard(myReader);
      	card.translateToCard();
        writerInfo.write(CardUtils.getString(card));
        cardCount++;
      } else if(data.equals("STOP_ANKI")) {
        break;
      } else if(data.equals("---")) {
        continue;
      } else if(!data.isEmpty()) {
        System.out.println("Leftover data. Did something get missed?");
        System.out.println(data);
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
