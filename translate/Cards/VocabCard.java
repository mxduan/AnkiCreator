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

public class VocabCard implements Card {
  String mainJapaneseField = "";
  String mainEnglishField = "";
  String additionalInformation = "";
  String memorizationHint = "";
  String tags = "";
  Scanner noteScanner;

  public VocabCard(Scanner scan) {
      noteScanner = scan;
  }

  public boolean errorCheck() {
    if(mainJapaneseField.isEmpty() || mainEnglishField.isEmpty() || additionalInformation.isEmpty() || memorizationHint.isEmpty()) {
      System.out.println("Field missing");
      return false;
    }
    if(mainJapaneseField.indexOf("-") != -1 || mainJapaneseField.indexOf("#") != -1 
      || mainEnglishField.indexOf("-") != -1 || mainEnglishField.indexOf("#") != -1 
      || additionalInformation.indexOf("-") != -1 || additionalInformation.indexOf("#") != -1 
      || memorizationHint.indexOf("-") != -1 || memorizationHint.indexOf("#") != -1) {
      System.out.println("Extra - or # field");
      return false;
    }
    return true;
  }

	public void translateToCard() {
    String data = CardUtils.findNextLineAndRemoveStars(noteScanner);
    
    mainJapaneseField = data.substring(0, data.indexOf("-"));
    int indexForEnglish = data.indexOf("#") == -1 ? data.length() : data.indexOf("#");
    int firstLetterPastDash = CardUtils.findFirstLetter(data, data.indexOf("-"));
    mainEnglishField = data.substring(data.indexOf("-") + 2, indexForEnglish);
    tags = CardUtils.getTags(data);

    additionalInformation = CardUtils.findNextLineAndRemoveStars(noteScanner);
    memorizationHint = CardUtils.findNextLineAndRemoveStars(noteScanner);
	}
}
