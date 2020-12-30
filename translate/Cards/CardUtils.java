package translate;

import java.io.*;
import java.io.File; // Import the File class
import java.io.FileNotFoundException; // Import this class to handle errors
import java.lang.reflect.*;
import java.util.*;
import java.util.Scanner; // Import the Scanner class to read text files

public final class CardUtils {
  private CardUtils() {}

  /**
   * Uses reflection to print out every string member variable in card onto one string. Card {
   * String foo = "foo" String bar = "bar" } Becomes "foo#bar\n"
   */
  public static String getString(Card card) throws IllegalAccessException, NoSuchFieldException {
    if (!card.errorCheck()) {
      throw new RuntimeException("Error check failed. Something's wrong.");
    }
    Class<?> objClass = card.getClass();
    // System.out.println("obj: " + card.getClass());

    Field[] fields = objClass.getDeclaredFields();
    StringBuilder result = new StringBuilder();
    for (Field field : fields) {
      if (!field.getType().getName().contains("String")) {
        continue;
      }
      field.setAccessible(true);
      //  System.out.println("Field: " + field.getName() + " value: " + field.get(card));
      result.append((String) field.get(card));
      result.append("#");
    }
    if (result.length() > 0) {
      result.setLength(result.length() - 1);
      result.append("\n");
    }
    return result.toString();
  }

  public static String findNextLineAndRemoveStars(PeekableScanner scan) {
    String starredString = "";
    while (scan.hasNextLine()) {
      if (scan.peek().equals("---")) {
        return "";
      }
      starredString = scan.nextLine();
      if (starredString.isEmpty()) {
        continue;
      } else {
        break;
      }
    }

    StringBuilder starsRemoved = new StringBuilder();
    for (int i = 0; i < starredString.length(); ++i) {
      if (starredString.charAt(i) == '*') {
        continue;
      }
      starsRemoved.append(starredString.charAt(i));
    }
    return starsRemoved.toString();
  }

  public static void printOutDifferences(String expected, String actual) {
    int shorterLength = expected.length() < actual.length() ? expected.length() : actual.length();
    for (int i = 0; i < shorterLength; ++i) {
      // if (expected.charAt(i) != actual.charAt(i)) {
      System.out.println(
          "Expected string has: "
              + expected.charAt(i)
              + " at "
              + i
              + " while Actual string has: "
              + actual.charAt(i));
      // }
    }
  }

  public static boolean verify(String className) throws FileNotFoundException, java.lang.Exception {
    String fileName = new StringBuilder(className).append("Test.txt").toString();
    PeekableScanner myReader = new PeekableScanner(fileName);

    Class<?> clazz = Class.forName(new StringBuilder("translate.").append(className).toString());
    Constructor<?> ctor = clazz.getConstructor(PeekableScanner.class);
    Object object = ctor.newInstance(new Object[] {myReader});
    ((Card) object).translateToCard();

    String correctCardFileName = new StringBuilder(className).append("Verification.txt").toString();
    File correctCardFile = new File(correctCardFileName);
    Scanner correctReader = new Scanner(correctCardFile);
    String correctString = correctReader.nextLine();

    String producedString = getString((Card) object);

    if (producedString.charAt(producedString.length() - 1) == '\n') {
      producedString = producedString.substring(0, producedString.length() - 1);
    }
    boolean correct = producedString.equals(correctString);

    if (!correct) {
      System.out.println(producedString);
      if (producedString.length() != correctString.length()) {
        System.out.println(
            "Expected length: "
                + correctString.length()
                + " Actual length: "
                + producedString.length());
      }
      CardUtils.printOutDifferences(correctString, producedString);
    }
    return correct;
  }

  public static String getTags(String data) {
    int indexOfHashtag = data.indexOf("#");
    if (indexOfHashtag == -1) {
      return "";
    }
    String tagsData = data.substring(indexOfHashtag);

    StringBuilder tags = new StringBuilder();
    for (int index = 0; index < tagsData.length(); ++index) {
      if (tagsData.charAt(index) == '#') {
        continue;
      }
      tags.append(tagsData.charAt(index));
    }
    return tags.toString();
  }

  public static int findFirstLetter(String data, int indexOfDash) {
    for (int index = indexOfDash; index < data.length(); ++index) {
      if (Character.isLetter(data.charAt(index))) {
        return index;
      }
    }
    return -1;
  }
}
