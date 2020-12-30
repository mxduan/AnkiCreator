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

public class InfoCard implements Card {
  String mainJapaneseField = "";
  String mainEnglishField = "";
  String additionalInformation = "";
  String tags = "";
  PeekableScanner noteScanner;

  public InfoCard(PeekableScanner scan) {
      noteScanner = scan;
  }

  public boolean errorCheck() {
    if(mainJapaneseField.isEmpty() || mainEnglishField.isEmpty()) {
      return false;
    }
    return true;
  }

	public void translateToCard() {
      String data = CardUtils.findNextLineAndRemoveStars(noteScanner);
		  String japaneseData;
	    if (data.indexOf('#') != -1) {
	      mainJapaneseField = data.substring(0, data.indexOf('#'));
	    } else {
	      mainJapaneseField = data;
	    }
      tags = CardUtils.getTags(data);

	    mainEnglishField = CardUtils.findNextLineAndRemoveStars(noteScanner);
      String potentialAdditionalInformation = CardUtils.findNextLineAndRemoveStars(noteScanner);
      if(!potentialAdditionalInformation.equals("---")) {
	      additionalInformation = potentialAdditionalInformation;
      }
	}
}
