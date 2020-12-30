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
  PeekableScanner noteScanner;

  public VocabCard(PeekableScanner scan) {
      noteScanner = scan;
  }

  public boolean errorCheck() {
    if(mainJapaneseField.isEmpty() || mainEnglishField.isEmpty()) {
      System.out.println(mainJapaneseField + " has failed");
      return false;
    }
    return true;
  }

	public void translateToCard() {
    String data = CardUtils.findNextLineAndRemoveStars(noteScanner);
    if (data.indexOf('#') != -1) {
      mainJapaneseField = data.substring(0, data.indexOf('#'));
    } else {
      mainJapaneseField = data;
    }
    tags = CardUtils.getTags(data);

    mainEnglishField = CardUtils.findNextLineAndRemoveStars(noteScanner);
    additionalInformation = CardUtils.findNextLineAndRemoveStars(noteScanner);
    memorizationHint = CardUtils.findNextLineAndRemoveStars(noteScanner);
	}
}
