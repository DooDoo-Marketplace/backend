package space.rebot.micro.marketservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.rebot.micro.marketservice.model.Sku;
import space.rebot.micro.marketservice.model.Word;
import space.rebot.micro.marketservice.model.WordSkus;
import space.rebot.micro.marketservice.repository.DictionaryRepository;
import space.rebot.micro.marketservice.repository.DictionarySkusRepository;
import space.rebot.micro.marketservice.repository.SkuRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class DictionaryService {

    @Autowired
    private DictionaryRepository dictionaryRepository;
    @Autowired
    private DictionarySkusRepository dictionarySkusRepository;
    @Autowired
    private SkuRepository skuRepository;

    public void addWordsToDictionary(String name, Long skuId) {
        String [] words = name.split(" \t");
        for (String word : words) {
            if (dictionaryRepository.getWordByWord(word.toLowerCase()) == null) {
                dictionaryRepository.save(new Word(word.toLowerCase()));
            }
            dictionarySkusRepository.save(new WordSkus(
                    dictionaryRepository.getWordById(dictionaryRepository.getWordId(word.toLowerCase())),
                    skuRepository.getSkuById(skuId)
            ));
        }
    }

    public List<Sku> findSimilarWords(String wordToCompare) {
        List<Word> db = dictionaryRepository.findAll();
        List<Long> minWordsIndexes = new ArrayList<>();
        int minDiff = wordToCompare.length() / 3 + 1;
        for (Word dbWord : db) {
            int difference = countDifference(dbWord.getWord(), wordToCompare.toLowerCase());
            if (difference < minDiff) {
                minWordsIndexes.clear();
                minWordsIndexes.add(dbWord.getId());
                minDiff = difference;
            } else if (difference == minDiff) {
                minWordsIndexes.add(dbWord.getId());
            }
        }
        List<Sku> similarSkus = new ArrayList<>();
        for (Long idx : minWordsIndexes) {
            List<Long> skuIds = dictionarySkusRepository.getSkuIdsByDictionaryId(idx);
            for (Long skuId : skuIds) {
                similarSkus.add(skuRepository.getSkuById(skuId));
            }
        }
        return similarSkus;
    }

    private int countDifference(String wordFromDB, String wordFromRequest) {
        char [] db = wordFromDB.toCharArray();
        char [] request = wordFromRequest.toCharArray();
        int [][] levenshteinDistance = new int[db.length + 1][request.length + 1];
        for (int i = 0; i < db.length + 1; i++) {
            for (int j = 0; j < request.length + 1; j++) {
                if (i == 0 && j == 0) {
                    levenshteinDistance[i][j] = 0;
                } else if (i == 0) {
                    levenshteinDistance[i][j] = j;
                } else if (j == 0) {
                    levenshteinDistance[i][j] = i;
                } else {
                    levenshteinDistance[i][j] = Math.min(
                            Math.min(levenshteinDistance[i][j - 1] + 1,
                                    levenshteinDistance[i - 1][j] + 1),
                            levenshteinDistance[i - 1][j - 1] + (db[i - 1] == request[j - 1] ? 0 : 1)
                    );
                }
            }
        }
        return levenshteinDistance[db.length][request.length];
    }
}
