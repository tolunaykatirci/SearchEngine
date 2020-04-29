import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Word {

    //parameters
    private String word;
    private Location location;

    // constructor
    public Word(String word, int line, int index) {
        this.word = word;
        location = new Location(line, index);
    }
}
