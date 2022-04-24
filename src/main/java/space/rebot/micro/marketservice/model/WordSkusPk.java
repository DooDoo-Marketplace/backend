package space.rebot.micro.marketservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.IdClass;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(WordSkusPk.class)
public class WordSkusPk implements Serializable {
    private long dictionary;
    private long sku;
}
