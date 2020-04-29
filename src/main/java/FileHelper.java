import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHelper {


    // find all words in a file
    public static List<Word> findWords(File file) {

        List<Word> wordList = new ArrayList<Word>();

        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(file);
            br = new BufferedReader(fr);

            // read line by line
            String line;
            int lineCount = 1;
            while ((line = br.readLine()) != null) {
                int indexCount = 1;

                // remove some punctuation
                line = line.trim();
                line = line.replace("-", " ");
                line = line.replace(".", " ");
                line = line.replace(",", " ");
                line = line.replace("!", " ");
                line = line.replace("?", " ");
                line = line.replace(":", " ");
                line = line.replace(";", " ");
                line = line.replace("\"", " ");
                line = line.replace("(", " ");
                line = line.replace(")", " ");

                String[] words = line.split(" ");
                for (String word:words) {
                    indexCount++; // increase for each space
                    if (word.length() > 0){
                        // add words to a list
                        wordList.add(new Word(word, lineCount, indexCount));
                        indexCount += word.length();
                    }
                }

                lineCount += 1;
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // close file reader and buffered readera
            try {
                assert fr != null;
                fr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert br != null;
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return wordList;
    }
}
