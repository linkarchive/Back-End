package project.linkarchive.backend.badword;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BadWordList {

    public static final Set<String> badWords = new HashSet<>(Arrays.asList(
            "시발", "씨발"
    ));

}