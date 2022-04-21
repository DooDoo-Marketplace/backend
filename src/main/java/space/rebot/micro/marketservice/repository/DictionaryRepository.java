package space.rebot.micro.marketservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import space.rebot.micro.marketservice.model.Word;

public interface DictionaryRepository extends JpaRepository<Word, Long> {
    Word getWordByWord(String word);

    Word getWordById(Long id);

    @Query(value = "select dc.id from dictionary dc where dc.word = :word", nativeQuery = true)
    Long getWordId(@Param("word") String word);
}
