import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TrieNode {

    // parameters
    private HashMap<Character, TrieNode> children = null; // child trie nodes
    private String content; // content
    private boolean isWord; // word boolean
    private List<Location> locations = new ArrayList<Location>(); // location of words in file
    private HashSet<String> fileNames = new HashSet<String>(); // file names which words in
}
