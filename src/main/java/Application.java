import java.io.File;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {

        // scanner for user input
        Scanner scanner = new Scanner(System.in);  // Create a Scanner object
        boolean isQuit = false;

        System.out.println("Welcome to Mini Search Engine!");

        // user interface menu
        while (!isQuit) {

            System.out.println("\nPlease select an operation:");
            System.out.println("1. Search a query in all txt files in folder");
            System.out.println("2. Find common word(s) in txt files");
            System.out.println("3. Quit");
            System.out.print("Your Selection: ");

            // error check
            int userInput;
            try {
                userInput = scanner.nextInt();  // Read user input
            } catch (InputMismatchException e) {
                // wrong input
                System.out.println("Please enter only integer!");
                scanner.nextLine();
                continue;
            }

            //error check
            if (userInput < 1 || userInput > 3) {
                // wrong input
                System.out.println("Please enter only 1-3!");
                scanner.nextLine();
                continue;
            } else if (userInput == 1) {
                // query searching
                scanner.nextLine();
                System.out.print("Please enter folder path: ");
                String folderPath = scanner.nextLine();
                List<File> txtFilesInFolder = new ArrayList<File>();
                try {
                    File directory = new File(folderPath);
                    File[] filesInFolder = directory.listFiles();

                    if (filesInFolder == null) {
                        System.out.println("Please enter valid folder path!");
                        continue;
                    }
                    for (File f : filesInFolder) {
                        if (f.getName().endsWith(".txt"))
                            txtFilesInFolder.add(f);
                    }

                    // all txt files in specified folder
                    if (txtFilesInFolder.size() == 0) {
                        System.out.println("There is no '.txt' file. Please enter valid folder path!");
                        continue;
                    }
                } catch (Exception e) { // error check
                    System.out.println("Please enter valid folder path!");
                    continue;
                }

                // txt files selected successfully
                System.out.println("Text files in specified path:");
                for (File f : txtFilesInFolder)
                    System.out.println(f.getAbsolutePath());


                // add all words in files to tries
                List<Trie> fileTries = new ArrayList<Trie>();
                System.out.println("Text files are processing...");
                for (File f : txtFilesInFolder) {
                    Trie trie = new Trie();
                    trie.setFileName(f.getName());
                    List<Word> wordList = FileHelper.findWords(f);
                    for (Word w:wordList) {
                        trie.insert(w, null);
                    }
                    fileTries.add(trie);
                }
                System.out.println("Processing finished!");
                System.out.print("Please enter the search query: ");
                String query = scanner.nextLine();

                // search query
                for(Trie trie: fileTries){
                    trie.search(query);
                }
                System.out.println("All Words found!");

            } else if (userInput == 2) {
                // common words
                scanner.nextLine();
                System.out.println("\nFind common words in files");

                // add new menu

                List<File> commonFiles = new ArrayList<File>();

                boolean isCommonQuit = false;
                while(!isCommonQuit){

                    if(commonFiles.size() > 0){
                        System.out.print("Selected Files: ");
                        for(File f: commonFiles){
                            System.out.print(f.getName());
                            System.out.print(" ");
                        }
                        System.out.println();
                    }

                    // second menu
                    System.out.println("\nPlease select an operation: ");
                    System.out.println("1. Add new file");
                    System.out.println("2. Find common word(s)");
                    System.out.println("3. Main menu");
                    System.out.print("Your Selection: ");

                    // error check
                    int userInput2;
                    try {
                        userInput2 = scanner.nextInt();  // Read user input
                    } catch (InputMismatchException e) {
                        // wrong input
                        System.out.println("Please enter only integer!");
                        scanner.nextLine();
                        continue;
                    }

                    // error check
                    if (userInput2 < 1 || userInput2 > 3) {
                        // wrong input
                        System.out.println("Please enter only 1-3!");
                        scanner.nextLine();
                        continue;
                    } else if (userInput2 == 1) {
                        // add file
                        scanner.nextLine();
                        System.out.print("Please enter file path: ");
                        String filePath = scanner.nextLine();

                        // check file
                        File f = new File(filePath);
                        if(f.exists() && !f.isDirectory() && f.getName().endsWith(".txt")) {

                            boolean i = false;
                            for(File file : commonFiles){
                                if(file.getAbsolutePath().equals(f.getAbsolutePath())){
                                    i = true;
                                    break;
                                }
                            }
                            if(i){
                                // our list has already include the specified file
                                System.out.println("This file has already selected!");
                            } else {
                                commonFiles.add(f);
                            }
                        } else {
                            System.out.println("Text file could not found!");
                        }

                    } else if (userInput2 == 2){

                        if(commonFiles.size() < 2){
                            System.out.print("You must select at least two files to find common words!");
                            continue;
                        } else {
                            // find common words
                            Trie commonTrie = new Trie();
                            for(File f: commonFiles){
                                List<Word> words = FileHelper.findWords(f);
                                for (Word w:words) {
                                    commonTrie.insert(w, f.getAbsolutePath());
                                }
                            }

                            // print common words in files
                            System.out.println("Common Words:");
                            commonTrie.printCommonWords(null, commonFiles.size());

                        }
                    } else {
                        scanner.nextLine();
                        isCommonQuit = true;
                    }

                }
            } else {
                // quit selected
                System.out.println("Goodbye!");
                isQuit = true;
            }

        }

    }
}
