package trie;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
        String acc = "";
        return print(root, 1, acc, words);
    }

    private static String print(TrieNode root, int indent, String acc, String[] words) {
        if (root == null) {
            return acc;
        }
        for (int i=0; i < indent-1; i++) {
            acc += "    ";
        }

        if (root.substr != null) {
            String pre = words[root.substr.wordIndex]
                    .substring(0, root.substr.endIndex+1);
            acc += "      " + pre + "\n";
        }

        for (int i=0; i < indent-1; i++) {
            acc += "    ";
        }
        acc += " ---";
        if (root.substr == null) {
            acc += "root\n";
        } else {
            acc += root.substr + "\n";
        }

        for (TrieNode ptr=root.firstChild; ptr != null; ptr=ptr.sibling) {
            for (int i=0; i < indent-1; i++) {
                acc += "    ";
            }
            acc += "     |\n";
            return print(ptr, indent+1, acc, words);
        }
        return acc;
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11})
    void buildTree(int trie) throws IOException {
        generateTrie("./src/test/resources/buildTree/inputs/trie" + trie + ".txt");
        String solution = Files.readString(Path.of("./src/test/resources/buildTree/outputs/trie" + trie + ".txt"), StandardCharsets.UTF_8);
        Assertions.assertThat(builtTree).isEqualTo(solution);
    }
}
