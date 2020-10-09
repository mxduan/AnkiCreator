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

public class ClozerCard {
  String mainJapaneseField = "";
  String mainEnglishField = "";
  String additionalInformation = "";
  String tags = "";
  Scanner noteScanner;

  public ClozerCard(Scanner scan) {
      noteScanner = scan;
  }

  private boolean errorCheck() {
    if(mainJapaneseField.isEmpty() || mainEnglishField.isEmpty() || additionalInformation.isEmpty()) {
      return false;
    }
    if(mainJapaneseField.indexOf("-") != -1 || mainJapaneseField.indexOf("#") != -1 
      || mainEnglishField.indexOf("-") != -1 || mainEnglishField.indexOf("#") != -1 
      || additionalInformation.indexOf("-") != -1 || additionalInformation.indexOf("#") != -1) {
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
    result.append("#");
    result.append(additionalInformation);
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
    String clozerData;
    String tagsData;
    if (data.indexOf('#') != -1) {
      clozerData = data.substring(0, data.indexOf('#'));
      tagsData = data.substring(data.indexOf('#'));
    } else {
      clozerData = data;
      tagsData = "";
    }

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

    StringBuilder tagsField = new StringBuilder();
    for(int index = 0; index < tagsData.length(); ++index) {
        if (tagsData.charAt(index) == '#') {
        continue;
      }
      tagsField.append(tagsData.charAt(index));
    }
    tags = tagsField.toString();

    mainEnglishField = findNextLineAndRemoveStars(noteScanner);
    additionalInformation = findNextLineAndRemoveStars(noteScanner);
}

private static void printOutDifferences(String expected, String actual) {
  int shorterLength = expected.length() < actual.length() ? expected.length() : actual.length();
  for(int i = 0; i < shorterLength; ++i) {
    if(expected.charAt(i) != actual.charAt(i)) {
      System.out.println("Expected string has: " + expected.charAt(i) + " at " + i + " while Actual string has: " + actual.charAt(i));
    }
  }
}

public static boolean verifyCode() throws FileNotFoundException, java.lang.Exception {
      File myObj = new File("ClozerCardTest.txt");
      Scanner myReader = new Scanner(myObj);
      ClozerCard card = new ClozerCard(myReader);
      card.translateToCard();

      File correctCardFile = new File("ClozerCardVerification.txt");
      Scanner correctReader = new Scanner(correctCardFile);
      String correctString = correctReader.nextLine();

      String producedString = card.getString();
      if (producedString.charAt(producedString.length()-1) == '\n') {
        producedString = producedString.substring(0, producedString.length() - 1);
      }
      boolean correct = producedString.equals(correctString);

      if (!correct) {
        System.out.println(producedString);
        if (producedString.length() != correctString.length()) {
          System.out.println("Expected length: " + correctString.length() + " Actual length: " + producedString.length());
        }
        printOutDifferences(correctString, producedString);
      }
      return correct;
  }
}
