package space.rebot.micro.marketservice.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "dictionary_skus")
@NoArgsConstructor
@IdClass(WordSkusPk.class)
public class WordSkus {
    @Id
    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "dictionary_id")
    private Word dictionary;

    @Id
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "sku_id")
    private Sku sku;

    public WordSkus(Word dictionary, Sku sku) {
        this.dictionary = dictionary;
        this.sku = sku;
    }
}
