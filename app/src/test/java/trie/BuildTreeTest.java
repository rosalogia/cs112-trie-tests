package trie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class BuildTreeTest {
    String builtTree = "";

    private void generateTrie(String filename) throws FileNotFoundException {
        Scanner sc = new Scanner(new File(filename));
        int numWords = Integer.parseInt(sc.nextLine());

        String[] allWords = new String[numWords];
        for (int i = 0; i < allWords.length; i++) {
            allWords[i] = sc.nextLine().trim().toLowerCase();
        }
        sc.close();

        TrieNode trie = Trie.buildTrie(allWords);
        builtTree = trieString(trie, allWords);
    }

    private String trieString(TrieNode root, String[] words) {
        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        // IMPORTANT: Save the old System.out!
        PrintStream old = System.out;
        // Tell Java to use your special stream
        System.setOut(ps);
        // Print some output: goes to your special stream
        Trie.print(root, words);
        // Put things back
        System.out.flush();
        System.setOut(old);
        // Show what happened
        return baos.toString();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    void buildTree(int trie) throws IOException {
        generateTrie("./src/test/resources/buildTree/inputs/trie" + trie + ".txt");
        String solution = Files.readString(Path.of("./src/test/resources/buildTree/outputs/trie" + trie + ".txt"), StandardCharsets.UTF_8);
        Assertions.assertThat(builtTree).isEqualTo(solution);
    }
}
