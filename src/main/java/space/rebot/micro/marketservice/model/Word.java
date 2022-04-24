package space.rebot.micro.marketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dictionary")
@NoArgsConstructor
public class Word {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "word", nullable = false)
    private String word;

    public Word(String word) {
        this.word = word;
    }
}
