import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Location {
    // location of word in a file (line and index)
    private int line;
    private int index;
}
