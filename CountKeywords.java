import java.util.*;
import java.io.*;

public class CountKeywords {
    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter a Java source file: ");
        String filename = input.nextLine();

        File file = new File(filename);
        if (file.exists()) {
            System.out.println("The number of keywords in " + filename
                    + " is " + countKeywords(file));
        } else {
            System.out.println("File " + filename + " does not exist");
        }
    }

    public static int countKeywords(File file) throws Exception {
        //array of keywords
        String[] keywordString = {"abstract", "assert", "boolean",
                "break", "byte", "case", "catch", "char", "class", "const",
                "continue", "default", "do", "double", "else", "enum",
                "extends", "for", "final", "finally", "float", "goto",
                "if", "implements", "import", "instanceof", "int",
                "interface", "long", "native", "new", "package", "private",
                "protected", "public", "return", "short", "static",
                "strictfp", "super", "switch", "synchronized", "this",
                "throw", "throws", "transient", "try", "void", "volatile",
                "while", "true", "false", "null"};
        
        Set<String> keywordSet = new HashSet<>(Arrays.asList(keywordString));
        int count = 0;
        boolean inComment = false; //check if in comment
        boolean inString = false; //check if in string

        Scanner input = new Scanner(file);

        while (input.hasNext()) {
            String line = input.nextLine();

            for (int i = 0; i < line.length(); i++) {
                char ch = line.charAt(i);

                if (ch == '"') {
                    inString = !inString;
                }

                if (!inString) {
                    if (ch == '/') {
                        if (i + 1 < line.length() && line.charAt(i + 1) == '/') {
                            //if line comment, skip
                            break;
                        } else if (i + 1 < line.length() && line.charAt(i + 1) == '*') {
                            //start of comment
                            inComment = true;
                            i++; 
                        }
                    } else if (ch == '*' && i + 1 < line.length() && line.charAt(i + 1) == '/') {
                        //end comment
                        inComment = false;
                        i++; 
                    }
                }

                if (!inComment && !inString && Character.isLetter(ch)) {
                    StringBuilder wordBuilder = new StringBuilder();
                    while (i < line.length() && Character.isLetter(line.charAt(i))) {
                        wordBuilder.append(line.charAt(i));
                        i++;
                    }
                    String word = wordBuilder.toString();
                    if (keywordSet.contains(word)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
