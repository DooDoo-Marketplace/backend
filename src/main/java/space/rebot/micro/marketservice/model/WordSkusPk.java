package space.rebot.micro.marketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WordSkusPk.class)
public class WordSkusPk implements Serializable {
    private long dictionary;
    private long sku;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WordSkusPk that = (WordSkusPk) o;
        return dictionary == that.dictionary && sku == that.sku;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dictionary, sku);
    }
}
