package translate;

import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.Scanner;

public class PeekableScanner
{
    private Scanner scan;
    private String next;

    public PeekableScanner( String source ) throws java.lang.Exception
    {
        File myObj = new File(source);
        scan = new Scanner( myObj );
        next = (scan.hasNextLine() ? scan.nextLine() : null);
    }

    public boolean hasNextLine()
    {
        return (next != null);
    }

    public String nextLine()
    {
        String current = next;
        next = (scan.hasNextLine() ? scan.nextLine() : null);
        return current;
    }

    public String peek()
    {
        return next;
    }

    public void close() {
        scan.close();
    }
}
