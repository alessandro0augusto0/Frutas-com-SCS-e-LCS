import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Alessandro Augusto
 * @since 20/01/2025
 */

public class SCS {

    // função p calcular a LCS (Longest Common Subsequence)
    private static String lcs(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

        // aqui preenche a matriz dp para encontrar a LCS
        for (int i = 0; i <= m; i++) {
            for (int j = 0; j <= n; j++) {
                if (i == 0 || j == 0) {
                    dp[i][j] = 0;
                } else if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        // reconstruir a LCS
        int i = m, j = n;
        StringBuilder lcs = new StringBuilder();
        while (i > 0 && j > 0) {
            if (str1.charAt(i - 1) == str2.charAt(j - 1)) {
                lcs.insert(0, str1.charAt(i - 1));
                i--;
                j--;
            } else if (dp[i - 1][j] > dp[i][j - 1]) {
                i--;
            } else {
                j--;
            }
        }

        return lcs.toString();
    }

    // função p calcular a SCS (Shortest Common Supersequence)
    private static String shortestCommonSupersequence(String str1, String str2) {
        // encontrando a LCS entre as duas strings
        String lcs = lcs(str1, str2);
        StringBuilder result = new StringBuilder();
        int p1 = 0, p2 = 0, p3 = 0;
        int len = lcs.length();

        // construindo a SCS intercalando os caracteres de str1, str2 e LCS
        while (p3 < len) {
            // add caracteres de str1 antes de LCS
            while (p1 < str1.length() && str1.charAt(p1) != lcs.charAt(p3)) {
                result.append(str1.charAt(p1));
                p1++;
            }
            // add caracteres de str2 antes de LCS
            while (p2 < str2.length() && str2.charAt(p2) != lcs.charAt(p3)) {
                result.append(str2.charAt(p2));
                p2++;
            }
            // add o caractere da LCS
            result.append(lcs.charAt(p3));
            p1++;
            p2++;
            p3++;
        }

        // add o restante de str1 ou str2 após a LCS
        while (p1 < str1.length()) {
            result.append(str1.charAt(p1));
            p1++;
        }

        while (p2 < str2.length()) {
            result.append(str2.charAt(p2));
            p2++;
        }

        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        // leitura da entrada usando BufferedReader para maior eficiência
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        StringBuilder result = new StringBuilder();
        long totalDuration = 0;

        // le a entrada até que uma linha vazia seja fornecida
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            String[] fruits = line.split(" ");
            if (fruits.length < 2) {
                System.out.println("Por favor, insira duas frutas separadas por espaço.");
                continue;
            }
            String fruit1 = fruits[0];
            String fruit2 = fruits[1];

            // mede o tempo de execução do cálculo da SCS
            long startTime = System.nanoTime();
            String scsResult = shortestCommonSupersequence(fruit1, fruit2);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalDuration += duration;

            // adiciona o resultado e o tempo de execução ao resultado final
            result.append(scsResult).append(" (Tempo de execução: ").append(duration).append(" nanosegundos)").append("\n");
        }

        // imprime o resultado final de uma vez
        System.out.print(result);

        // imprime o tempo total de execução
        System.out.println();
        System.out.println("Tempo total de execução: " + totalDuration + " nanosegundos");
        System.out.println("Tempo total de execução: " + (totalDuration / 1_000_000) + " milissegundos");
        System.out.println("Tempo total de execução: " + (totalDuration / 1_000_000_000) + " segundos");
    }
}
