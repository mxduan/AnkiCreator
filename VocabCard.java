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

public class VocabCard {
  String mainJapaneseField = "";
  String mainEnglishField = "";
  String additionalInformation = "";
  String memorizationHint = "";
  String tags = "";
  Scanner noteScanner;

  public VocabCard(Scanner scan) {
      noteScanner = scan;
  }

  private boolean errorCheck() {
    if(mainJapaneseField.isEmpty() || mainEnglishField.isEmpty() || additionalInformation.isEmpty() || memorizationHint.isEmpty()) {
      return false;
    }
    if(mainJapaneseField.indexOf("-") != -1 || mainJapaneseField.indexOf("#") != -1 
      || mainEnglishField.indexOf("-") != -1 || mainEnglishField.indexOf("#") != -1 
      || additionalInformation.indexOf("-") != -1 || additionalInformation.indexOf("#") != -1 
      || memorizationHint.indexOf("-") != -1 || memorizationHint.indexOf("#") != -1) {
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
    result.append("#");
    result.append(memorizationHint);
    result.append("#");
    result.append(tags);

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
    
    mainJapaneseField = data.substring(0, data.indexOf("-") - 1);
    mainEnglishField = data.substring(data.indexOf("-") + 2, data.indexOf("#") - 1);

    StringBuilder tagsField = new StringBuilder();
    for(int index = data.indexOf("#") + 1; index < data.length(); ++index) {
      if (data.charAt(index) == '#') {
        continue;
      }
      tagsField.append(data.charAt(index));
    }
    tags = tagsField.toString();

    additionalInformation = findNextLineAndRemoveStars(noteScanner);
    memorizationHint = findNextLineAndRemoveStars(noteScanner);
	}

  public static boolean verifyCode() throws FileNotFoundException {
      File myObj = new File("VocabCardTest.txt");
      Scanner myReader = new Scanner(myObj);
      VocabCard card = new VocabCard(myReader);
      card.translateToCard();

      File correctCardFile = new File("VocabCardVerification.txt");
      Scanner correctReader = new Scanner(correctCardFile);
      String correctCard = correctReader.nextLine();

      String producedString = correctCard.toString();

      boolean correct = producedString.equals(correctCard);

      if (!correct) {
        System.out.println("VocabCard: " + producedString);
      }
      return correct;
  }
}
