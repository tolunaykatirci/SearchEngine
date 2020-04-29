import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
public class Trie {

    private TrieNode root;      // root node
    private String fileName;    // file name

    // constructor
    public Trie() {
        root = new TrieNode();
        root.setContent(null);
        root.setWord(false);
        root.setChildren(new HashMap<Character, TrieNode>());
    }

    // insert a word to root node
    public void insert(Word wordObject, String fileName) {
        TrieNode current = root;

        String currentWord = wordObject.getWord().toLowerCase();
        Location currentLocation = wordObject.getLocation();

        for (int i = 0; i < currentWord.length(); i++) {

            // if children null, we created a new HashMap
            if (current.getChildren() == null)
                current.setChildren(new HashMap<Character, TrieNode>());

            // if children don't contain, we will add new node
            if (!current.getChildren().containsKey(currentWord.charAt(i))) {
                TrieNode node = new TrieNode();

                if (i == currentWord.length() - 1) {   // last element, it is word
                    node.setWord(true);
                    node.setContent(currentWord);
                    node.getLocations().add(currentLocation);

                    if(fileName != null)
                        node.getFileNames().add(fileName);

                } else {    // not last element
                    node.setWord(false);
                    node.setContent(currentWord.substring(0, i+1));
                    //node.getLocations().add(currentLocation);
                    node.setChildren(new HashMap<Character, TrieNode>());   // word will continue
                }
                current.getChildren().put(currentWord.charAt(i), node);

            } else {    // node already exists
                TrieNode node = current.getChildren().get(currentWord.charAt(i));

                if (i == currentWord.length() - 1) {   // last element, it is word
                    node.setWord(true);
                    node.getLocations().add(currentLocation);
                    if(fileName != null)
                        node.getFileNames().add(fileName);

                } else {    // not last element
                    // continue
                }
            }
            current = current.getChildren().get(currentWord.charAt(i));
        }
    }

    // print common words of a node
    public void printCommonWords(TrieNode node, int filesCount) {

        if(node == null)
            node = root;

        // if file counts are same, it means all files include this word
        if (node.isWord() && node.getFileNames().size() == filesCount) {
            System.out.println("Word: " + node.getContent());
        }
        // recursive method for all children
        if (node.getChildren() != null) {
            for (TrieNode tn : node.getChildren().values()) {
                printCommonWords(tn, filesCount);
            }
        }
    }

    // print all words in a trie
    public void printAllWords(TrieNode node, String fileName) {
        if (node.isWord()) {
            // print word
            System.out.println("File: " + fileName +", Word: " + node.getContent());
            //print location
            for (Location l : node.getLocations())
                System.out.println("\tline: " + l.getLine() + " \tindex: " + l.getIndex());
        }
        // recursive method
        if (node.getChildren() != null) {
            for (TrieNode tn : node.getChildren().values()) {
                printAllWords(tn, fileName);
            }
        }
    }

    // search a query in trie
    public void search(String query) {
        TrieNode current = root;
//        System.out.println("Query searching: " + query);
        for (int i = 0; i < query.length(); i++) {
            if (current.getChildren() != null){
                TrieNode node = current.getChildren().get(query.charAt(i));

                if (node == null)
                    break;

                // if we can reach the end of the query, we should print all child nodes of current node
                if( i == query.length() - 1)
                    printAllWords(node, fileName);
                else
                    current = node;
            }
        }
    }
}
