import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Alessandro Augusto
 * @since 20/01/2025
 */

public class AppGUI {
    private JFrame frame;
    private JTextArea fruitInputArea;
    private JTextArea resultArea;
    private JTextArea lcsArea;
    private JLabel lcsTimeLabel;
    private JLabel scsTimeLabel;

    public static void main(String[] args) {
        // Set Nimbus Look and Feel
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new AppGUI().createAndShowGUI());
    }

    private void createAndShowGUI() {
        frame = new JFrame("Fruit Name Combiner");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        // Centralizar a janela na tela
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(panel);

        placeComponents(panel);

        frame.setVisible(true);
    }

    private void placeComponents(JPanel panel) {
        // Painel de entrada com GridBagLayout
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Painel de entrada
        JPanel inputPanel = new JPanel(new BorderLayout(10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Enter Multiple Fruits", 0, 0, new Font("Arial", Font.BOLD, 14)));

        // campo de texto para entrada de várias frutas
        fruitInputArea = new JTextArea(5, 30);
        fruitInputArea.setLineWrap(true);
        fruitInputArea.setWrapStyleWord(true);
        fruitInputArea.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(fruitInputArea);
        inputPanel.add(scrollPane, BorderLayout.CENTER);

        // botão de combinação com feedback visual
        JButton combineButton = new JButton("Combine");
        combineButton.setFont(new Font("Arial", Font.BOLD, 14));
        combineButton.setBackground(new Color(70, 130, 180));
        combineButton.setForeground(Color.WHITE);
        combineButton.setFocusPainted(false);
        combineButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String inputText = fruitInputArea.getText().trim();
                if (!inputText.isEmpty()) {
                    // dividir a entrada em frutas
                    String[] lines = inputText.split("\\n");
                    StringBuilder lcsResult = new StringBuilder();
                    StringBuilder scsResult = new StringBuilder();
                    long totalExecutionTimeLCS = 0;
                    long totalExecutionTimeSCS = 0;

                    for (String line : lines) {
                        String[] fruits = line.split(" ");
                        if (fruits.length == 2) {
                            String fruit1 = fruits[0].trim();
                            String fruit2 = fruits[1].trim();

                            long startTime = System.nanoTime();
                            String lcs = lcs(fruit1, fruit2);
                            long endTime = System.nanoTime();
                            long executionTimeLCS = endTime - startTime;
                            totalExecutionTimeLCS += executionTimeLCS;

                            startTime = System.nanoTime();
                            String scs = shortestCommonSupersequence(fruit1, fruit2);
                            endTime = System.nanoTime();
                            long executionTimeSCS = endTime - startTime;
                            totalExecutionTimeSCS += executionTimeSCS;

                            lcsResult.append("LCS (" + fruit1 + " & " + fruit2 + "): " + lcs + " (" + executionTimeLCS + " ns)\n");
                            scsResult.append("SCS (" + fruit1 + " & " + fruit2 + "): " + scs + " (" + executionTimeSCS + " ns)\n");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Please enter exactly two fruit names per line.");
                        }
                    }

                    // atualiza as areas de texto com os resultados
                    lcsArea.setText(lcsResult.toString());
                    resultArea.setText(scsResult.toString());

                    // atualiza o tempo total de execução
                    lcsTimeLabel.setText(formatExecutionTime("LCS Execution Time: ", totalExecutionTimeLCS));
                    scsTimeLabel.setText(formatExecutionTime("SCS Execution Time: ", totalExecutionTimeSCS));
                } else {
                    JOptionPane.showMessageDialog(frame, "Please enter at least one fruit name.");
                }
            }
        });

        inputPanel.add(combineButton, BorderLayout.SOUTH);

        // area de resultado da SCS
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultArea.setBackground(new Color(255, 250, 205));
        resultArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Results (SCS)", 0, 0, new Font("Arial", Font.BOLD, 14)));

        // area de resultado da LCS
        lcsArea = new JTextArea();
        lcsArea.setEditable(false);
        lcsArea.setFont(new Font("Arial", Font.PLAIN, 14));
        lcsArea.setBackground(new Color(255, 250, 205));
        lcsArea.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Results (LCS)", 0, 0, new Font("Arial", Font.BOLD, 14)));

        // label para exibir o tempo de execução
        lcsTimeLabel = new JLabel("LCS Execution Time: 0 ns");
        lcsTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scsTimeLabel = new JLabel("SCS Execution Time: 0 ns");
        scsTimeLabel.setFont(new Font("Arial", Font.BOLD, 14));

        // add os componentes ao painel principal usando GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0.2;
        panel.add(inputPanel, gbc);

        gbc.gridy = 1;
        gbc.weighty = 0.4;
        panel.add(new JScrollPane(resultArea), gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.4;
        panel.add(new JScrollPane(lcsArea), gbc);

        gbc.gridy = 3;
        gbc.weighty = 0.05;
        panel.add(lcsTimeLabel, gbc);

        gbc.gridy = 4;
        gbc.weighty = 0.05;
        panel.add(scsTimeLabel, gbc);
    }

    private String formatExecutionTime(String label, long timeInNanoSeconds) {
        double timeInMilliSeconds = timeInNanoSeconds / 1_000_000.0;
        double timeInSeconds = timeInNanoSeconds / 1_000_000_000.0;
        return String.format("%s %d ns (%.3f ms, %.6f s)", label, timeInNanoSeconds, timeInMilliSeconds, timeInSeconds);
    }

    // função p calcular a subsequência comum mais longa (LCS)
    private static String lcs(String str1, String str2) {
        int m = str1.length();
        int n = str2.length();
        int[][] dp = new int[m + 1][n + 1];

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

    // Nova implementação da SCS baseada na LCS
    private static String shortestCommonSupersequence(String str1, String str2) {
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
}