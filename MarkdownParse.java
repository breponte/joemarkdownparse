// File reading code from https://howtodoinjava.com/java/io/java-read-file-to-string-examples/
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

// Structural idea: go line by line instead of [ by [
// Descriptive idea: add commenst to explain complex conditions
// Code size idea – can we combine continue cases into one condition
// Code clarity idea – can we use helper methods
// Fancy feature idea – can we learn more about regular expressions

public class MarkdownParse {
    public static ArrayList<String> getLinks(String markdown) {
        ArrayList<String> toReturn = new ArrayList<>();
        // find the next [, then find the ], then find the (, then take up to
        // the next )
        int currentIndex = 0;
        while(currentIndex < markdown.length()) {
            int nextOpenBracket = markdown.indexOf("[", currentIndex);
            System.out.format("%d\t%d\n", currentIndex, nextOpenBracket);
            if(nextOpenBracket == -1) {
                break;
            }
            if(nextOpenBracket > 0 && markdown.charAt(nextOpenBracket - 1) == '!') {
                currentIndex = nextOpenBracket + 1;
                continue;
            }
            // Plan: check for ] and then check for ( immediately after
            int nextCloseBracket = markdown.indexOf("]", nextOpenBracket);
            if(nextCloseBracket == -1) {
                break;
            }
            if(markdown.charAt(nextCloseBracket + 1) != '(') {
                currentIndex = nextCloseBracket + 1;
                continue;
            }
            int closeParen = markdown.indexOf(")", nextCloseBracket);
            toReturn.add(markdown.substring(nextCloseBracket + 2, closeParen));
            currentIndex = closeParen + 1;
            System.out.format("%d\t%d\t%d\t%d\n", currentIndex, nextOpenBracket, nextCloseBracket, closeParen);
        }
        return toReturn;
    }
    public static void main(String[] args) throws IOException {
		Path fileName = Path.of(args[0]);
	    String contents = Files.readString(fileName);
        ArrayList<String> links = getLinks(contents);
        System.out.println(links);
    }
}