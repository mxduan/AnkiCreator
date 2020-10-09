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

public class InfoCard {
  String mainJapaneseField = "";
  String mainEnglishField = "";
  String additionalInformation = "";
  String tags = "";
  Scanner noteScanner;

  public InfoCard(Scanner scan) {
      noteScanner = scan;
  }

  private boolean errorCheck() {
    if(mainJapaneseField.isEmpty() || mainEnglishField.isEmpty()) {
      return false;
    }
    if(mainJapaneseField.indexOf("-") != -1 || mainJapaneseField.indexOf("#") != -1 
      || mainEnglishField.indexOf("-") != -1 || mainEnglishField.indexOf("#") != -1) {
      return false;
    }
    return true;
  }

  private String getString() {
    if (!errorCheck()) {
      throw new RuntimeException("Error check failed. Something's wrong.");
    }
    StringBuilder result = new StringBuilder(mainJapaneseField);
    result.append("#");
    result.append(mainEnglishField);
    if (!additionalInformation.isEmpty()) {
      result.append("#");
      result.append(additionalInformation);
    }
    if (!tags.isEmpty()) {
      result.append("#");
      result.append(tags);
    }
    result.append("\n");
    return result.toString();
  }

  private String findNextLineAndRemoveStars(Scanner scan) {
    String starredString = "";
    while (scan.hasNextLine()) {
      starredString = scan.nextLine();
        if(starredString.isEmpty()) {
          continue;
        } else {
          break;
        }
    }
    if (starredString.isEmpty()) {
      throw new RuntimeException("Shouldn't be empty here");
    }
    StringBuilder starsRemoved = new StringBuilder();
    for(int i = 0; i < starredString.length(); ++i) {
      if (starredString.charAt(i) == '*') {
        continue;
      }
      starsRemoved.append(starredString.charAt(i));
    }
    return starsRemoved.toString();
  }

	public void printToFile(PrintWriter writer) {
		writer.write(this.getString());
	}

	public void translateToCard() {
      String data = findNextLineAndRemoveStars(noteScanner);
		  String japaneseData;
	    String tagsData;
	    if (data.indexOf('#') != -1) {
	      mainJapaneseField = data.substring(0, data.indexOf('#'));
	      tagsData = data.substring(data.indexOf('#'));
	    } else {
	      mainJapaneseField = data;
	      tagsData = "";
	    }

	    StringBuilder tagsField = new StringBuilder();
	    for(int index = 0; index < tagsData.length(); ++index) {
	        if (tagsData.charAt(index) == '#') {
	        continue;
	      }
	      tagsField.append(tagsData.charAt(index));
	    }
	    tags = tagsField.toString();

	    mainEnglishField = findNextLineAndRemoveStars(noteScanner);
      String potentialAdditionalInformation = findNextLineAndRemoveStars(noteScanner);
      if(!potentialAdditionalInformation.equals("---")) {
	      additionalInformation = potentialAdditionalInformation;
      }
	}

  public static boolean verifyCode() throws FileNotFoundException {
      File myObj = new File("InfoCardTest.txt");
      Scanner myReader = new Scanner(myObj);
      InfoCard card = new InfoCard(myReader);
      card.translateToCard();

      File correctCardFile = new File("InfoCardVerification.txt");
      Scanner correctReader = new Scanner(correctCardFile);
      String correctString = correctReader.nextLine();

      
      String producedString = card.getString();
      if (producedString.charAt(producedString.length()-1) == '\n') {
        producedString = producedString.substring(0, producedString.length() - 1);
      }
      boolean correct = producedString.equals(correctString);

      if (!correct) {
        System.out.println(producedString);
      }
      return correct;
  }
}
