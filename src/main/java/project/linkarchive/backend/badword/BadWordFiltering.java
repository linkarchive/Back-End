package project.linkarchive.backend.badword;

import org.springframework.stereotype.Service;

import java.util.Arrays;

import static project.linkarchive.backend.badword.BadWordList.badWords;

@Service
public class BadWordFiltering implements BadWordSystem {

    @Override
    public boolean filter(String text) {
        String[] words = text.split("\\s+");
        return Arrays.stream(words).anyMatch(word -> badWords.contains(word));
    }

}