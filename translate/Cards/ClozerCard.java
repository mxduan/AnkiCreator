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

public class ClozerCard implements Card {
  String mainJapaneseField = "";
  String mainEnglishField = "";
  String additionalInformation = "";
  String tags = "";
  Scanner noteScanner;

  public ClozerCard(Scanner scan) {
      noteScanner = scan;
  }

  public boolean errorCheck() {
    if(mainJapaneseField.isEmpty() || mainEnglishField.isEmpty() || additionalInformation.isEmpty()) {
      System.out.println(mainJapaneseField + " has failed");
      System.out.println(mainEnglishField);
      System.out.println(additionalInformation);
      System.out.println(tags);
      return false;
    }
    if(mainJapaneseField.indexOf("-") != -1 || mainJapaneseField.indexOf("#") != -1 
      || mainEnglishField.indexOf("-") != -1 || mainEnglishField.indexOf("#") != -1 
      || additionalInformation.indexOf("-") != -1 || additionalInformation.indexOf("#") != -1) {
      System.out.println(mainJapaneseField + " has failed");
      System.out.println(mainEnglishField);
      System.out.println(additionalInformation);
      System.out.println(tags);
      return false;
    }
    return true;
  }

public void translateToCard() {
    String data = CardUtils.findNextLineAndRemoveStars(noteScanner);
    String clozerData;
    if (data.indexOf('#') != -1) {
      clozerData = data.substring(0, data.indexOf('#'));
    } else {
      clozerData = data;
    }
    tags = CardUtils.getTags(data);

    StringBuilder japaneseField = new StringBuilder();
    for(int index = 0; index < clozerData.length(); ++index) {
        if (clozerData.charAt(index) == '{') {
      		japaneseField.append(clozerData.charAt(index));
      		japaneseField.append("{c1::");
      		continue;
        } else if (clozerData.charAt(index) == '}') {
      		japaneseField.append("}}");
      		continue;
		}
      japaneseField.append(clozerData.charAt(index));
    }
    mainJapaneseField = japaneseField.toString();

    mainEnglishField = CardUtils.findNextLineAndRemoveStars(noteScanner);
    additionalInformation = CardUtils.findNextLineAndRemoveStars(noteScanner);
}
}
