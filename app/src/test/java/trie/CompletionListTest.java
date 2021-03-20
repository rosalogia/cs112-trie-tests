package trie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class CompletionListTest {
    String completionList = "";

    private void generateTrie(String filename, String prefix) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        int numWords = Integer.parseInt(sc.nextLine());

        String[] allWords = new String[numWords];
        for (int i = 0; i < allWords.length; i++) {
            allWords[i] = sc.nextLine().trim().toLowerCase();
        }
        sc.close();

        TrieNode trie = Trie.buildTrie(allWords);
        ArrayList<TrieNode> completionResults = Trie.completionList(trie, allWords, prefix);
        StringBuilder output = new StringBuilder();

        for (TrieNode result : completionResults) {
            output.append(allWords[result.substr.wordIndex]).append(",");
        }

        completionList = output.toString();
    }

    @ParameterizedTest
    @ValueSource(ints = {3, 4, 5, 6, 7, 8, 10, 11})
    void testCompletionLists(int trie) throws IOException {
        Stream<String> lines = Files.lines(Path.of("./src/test/resources/completionList/inputs/trie" + trie + ".txt"), StandardCharsets.UTF_8);
        for (String line : lines.toArray(String[]::new)) {
            generateTrie("./src/test/resources/buildTree/inputs/trie" + trie + ".txt", line);
            String ans = Files.readString(Path.of("./src/test/resources/completionList/outputs/trie" + trie + "_" + line + ".txt"), StandardCharsets.UTF_8);
            Set<String> completions = new HashSet<>(Arrays.asList(ans.split(",")));
            Set<String> attempt = new HashSet<>((Arrays.asList(completionList.split(","))));
            Assertions.assertThat(attempt).isEqualTo(completions);
        }
    }
}
