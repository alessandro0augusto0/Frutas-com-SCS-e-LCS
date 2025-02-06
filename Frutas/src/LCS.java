import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Alessandro Augusto
 * @since 20/01/2025
 */

public class LCS {

    // função para calcular a LCS (Longest Common Subsequence)
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

    public static void main(String[] args) throws IOException {
        // leitura da entrada usando BufferedReader para maior eficiência
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        StringBuilder result = new StringBuilder();
        long totalDuration = 0;

        // lê a entrada até que uma linha vazia seja fornecida
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            String[] fruits = line.split(" ");
            if (fruits.length < 2) {
                System.out.println("Por favor, insira duas frutas separadas por espaço.");
                continue;
            }
            String fruit1 = fruits[0];
            String fruit2 = fruits[1];

            // mede o tempo de execução do cálculo da LCS
            long startTime = System.nanoTime();
            String lcsResult = lcs(fruit1, fruit2);
            long endTime = System.nanoTime();
            long duration = endTime - startTime;
            totalDuration += duration;

            // adiciona o resultado e o tempo de execução ao resultado final
            result.append(lcsResult).append(" (Tempo de execução: ").append(duration).append(" nanosegundos)").append("\n");
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