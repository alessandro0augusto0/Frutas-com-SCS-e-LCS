# 🍇🍈 Frutas com SCS e LCS 🍉🍍

Bem-vindo ao repositório **Frutas-com-SCS-e-LCS**! Este projeto foi desenvolvido como parte da disciplina de **Projeto e Análise de Algoritmos** no **IFSULDEMINAS - Campus Poços de Caldas**, sob a orientação do professor **Douglas Castilho**. Aqui, exploramos a criação de nomes criativos para novas frutas geradas a partir da combinação de duas frutas existentes, utilizando algoritmos eficientes para encontrar a **Subsequência Comum Mais Longa (LCS)** e a **Supersequência Comum Mais Curta (SCS)**.

## 🎯 Objetivo

O objetivo deste projeto é criar nomes curtos e criativos para novas frutas que são combinações de duas frutas existentes. A ideia é encontrar a menor palavra possível que contenha os nomes das duas frutas originais como subsequências. Por exemplo, a combinação de "maçã" e "pera" pode resultar em "maçã-pera", mas queremos algo mais compacto e interessante, como "applear".

## 🛠️ Funcionalidades

- **Cálculo da LCS (Longest Common Subsequence):** Encontra a maior subsequência comum entre duas strings.
- **Cálculo da SCS (Shortest Common Supersequence):** Encontra a menor supersequência comum que contém ambas as strings como subsequências.
- **Interface Gráfica Intuitiva:** Uma interface gráfica amigável para inserir os nomes das frutas e visualizar os resultados de forma clara e organizada.

## 📸 Screenshots

### Interface Antes de Ser Usada
![Interface Antes de Ser Usada](https://github.com/user-attachments/assets/d45bd2d1-f772-4793-84fe-2f908bb1ea60)

### Interface Após Mostrar os Resultados
![Interface Após Mostrar os Resultados](https://github.com/user-attachments/assets/9d4a0013-ab7d-462f-9b29-e77bc21f1bf6)

## 🚀 Como Usar

1. **Clone o Repositório:**
    ```bash
    git clone https://github.com/alessandro0augusto0/Frutas-com-SCS-e-LCS.git
    ```

2. **Compile e Execute:**

    Certifique-se de ter o Java instalado.

    Navegue até o diretório do projeto e compile o código:
    ```bash
    javac SCS.java AppGUI.java
    ```

    Execute a interface gráfica:
    ```bash
    java AppGUI
    ```

3. **Insira os Nomes das Frutas:**

    Na interface gráfica, insira os nomes das duas frutas que deseja combinar, separados por espaço.

    Clique no botão "Combine" para ver os resultados.

4. **Visualize os Resultados:**

    A interface exibirá a LCS e a SCS para cada par de frutas inserido.

    No terminal, é possível inserir vários pares de frutas. Quando não desejar mais inserir nenhum par e quiser visualizar os resultados, basta enviar uma linha em branco, ou seja, simplesmente pressionar Enter para quebrar a linha.

## 🧠 Lógica do Código

### 1. Problema

Uma empresa se especializou em criar novos tipos de frutas, transferindo genes entre duas frutas distintas. Na maioria das vezes, esse método não funciona, mas, algumas vezes, surge uma nova fruta com o gosto de uma mistura entre as duas. Um grande tópico de discussão dentro da empresa é: "Como as novas criações devem ser chamadas?" Uma mistura entre maçã e pera poderia ser chamada de "maçã-pera", mas isso não parece muito interessante. O gerente da empresa decide, então, utilizar a palavra mais curta que contenha os nomes das frutas originais como subsequências. Por exemplo, "applear" contém "apple" (maçã) e "pear" (pera), e não há palavra mais curta que tenha a mesma propriedade. Neste trabalho, você deve desenvolver um programa que compute um nome mais curto para uma combinação de duas frutas. Seu algoritmo deve ser eficiente, caso contrário, pode não conseguir executar no tempo necessário para nomes longos de frutas.

### 2. Entrada

Cada linha do arquivo de entrada contém duas palavras que representam os nomes das frutas a serem combinadas. Todos os nomes têm um comprimento máximo de 100 caracteres e consistem apenas de caracteres alfabéticos. A entrada é encerrada no final do arquivo e pode ser lida a partir de um arquivo ou da entrada padrão (teclado).

### 3. Saída

Para cada caso de teste, imprima o nome mais curto da fruta resultante em uma linha. Se houver mais de uma possibilidade para o nome mais curto, qualquer uma das opções é aceitável. A saída deve ser escrita na saída padrão e não em um arquivo.

## 📊 Exemplos de Entrada e Saída

**Entrada:**
```bash
apple peach
ananas banana
pear peach
```

**Saída:**
```bash
appleach
bananas
pearch
```

#### Função para calcular a LCS (Longest Common Subsequence)

A função `lcs` calcula a maior subsequência comum entre duas strings. Ela utiliza uma matriz `dp` para armazenar os comprimentos das subsequências comuns em diferentes pontos das strings. A matriz é preenchida iterativamente, e a LCS é reconstruída a partir da matriz.

#### Função para calcular a SCS (Shortest Common Supersequence)

A função `shortestCommonSupersequence` utiliza a LCS para construir a menor supersequência comum que contém ambas as strings como subsequências. Ela intercala os caracteres das duas strings e da LCS para formar a SCS.

#### Função Principal

A função `main` lê a entrada do usuário, processa cada par de frutas, calcula a SCS para cada par e imprime o resultado final.

```java
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SCS {

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

    private static String shortestCommonSupersequence(String str1, String str2) {
        String lcs = lcs(str1, str2);
        StringBuilder result = new StringBuilder();
        int p1 = 0, p2 = 0, p3 = 0;
        int len = lcs.length();

        while (p3 < len) {
            while (p1 < str1.length() && str1.charAt(p1) != lcs.charAt(p3)) {
                result.append(str1.charAt(p1));
                p1++;
            }
            while (p2 < str2.length() && str2.charAt(p2) != lcs.charAt(p3)) {
                result.append(str2.charAt(p2));
                p2++;
            }
            result.append(lcs.charAt(p3));
            p1++;
            p2++;
            p3++;
        }

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
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String line;
        StringBuilder result = new StringBuilder();
        
        while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
            String[] fruits = line.split(" ");
            if (fruits.length < 2) {
                System.out.println("Por favor, insira duas frutas separadas por espaço.");
                continue;
            }
            String fruit1 = fruits[0];
            String fruit2 = fruits[1];
            
            result.append(shortestCommonSupersequence(fruit1, fruit2)).append("\n");
        }
        
        System.out.print(result);
    }
}
```

## 📚 Tecnologias Utilizadas

- **Java:** Linguagem de programação principal.
- **Swing:** Biblioteca para criação da interface gráfica.
- **GitHub:** Plataforma de hospedagem e versionamento do código.

## 👨‍💻 Autor

**Alessandro Augusto**

- GitHub: [alessandro0augusto0](https://github.com/alessandro0augusto0)

Projeto desenvolvido como parte do curso de Projeto e Análise de Algoritmos no IFSULDEMINAS - Campus Poços de Caldas.

## 🙏 Agradecimentos

Agradeço ao professor **Douglas Castilho** pela orientação e suporte durante o desenvolvimento deste projeto.

Espero que este projeto seja útil e inspire novas ideias! Sinta-se à vontade para contribuir, reportar issues ou sugerir melhorias. 🚀

Happy Coding! 🎉

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>


---
### Algumas observações extras:
---
# Comparação de Tempos de Execução: Interface Gráfica vs. Terminal

Este projeto implementa a lógica para calcular a **Subsequência Comum Mais Longa (LCS)** e a **Supersequência Comum Mais Curta (SCS)** entre duas strings. O código está disponível em duas versões:

1. **Interface Gráfica (`AppGUI`)**: Usa Swing para fornecer uma interface amigável ao usuário.
2. **Terminal (`SCS`)**: Executa diretamente no console, sem interface gráfica.

Embora a lógica de cálculo da LCS e SCS seja a mesma nas duas versões, podem ocorrer divergências nos tempos de execução. Abaixo estão os principais motivos para essas diferenças:

---

## Motivos para Divergências nos Tempos de Execução

### 1. **Overhead da Interface Gráfica**
   - O `AppGUI` usa a biblioteca Swing para criar uma interface gráfica. O Swing roda na **Event Dispatch Thread (EDT)**, que é responsável por atualizar a interface e responder a eventos do usuário.
   - Qualquer operação que atualize a interface gráfica (como atualizar um `JTextArea` ou `JLabel`) deve ser executada na EDT, o que adiciona um overhead significativo ao tempo de execução.
   - No `SCS`, como não há interface gráfica, o tempo de execução é medido apenas para o cálculo da LCS e SCS, sem overhead adicional.

### 2. **Inicialização da Interface Gráfica**
   - A criação e inicialização de componentes gráficos (como `JFrame`, `JTextArea`, `JLabel`, etc.) consomem tempo e recursos do sistema.
   - No `SCS`, como não há inicialização de componentes gráficos, o tempo de execução é mais próximo do tempo real de cálculo.

### 3. **Operações de I/O Gráficas**
   - No `AppGUI`, a leitura da entrada e a exibição dos resultados envolvem operações de I/O gráficas, que são mais lentas do que operações de console.
   - No `SCS`, a entrada e saída são feitas diretamente no console, o que é mais eficiente.

### 4. **Gerenciamento de Memória**
   - A interface gráfica consome mais memória e recursos do sistema, o que pode impactar o desempenho geral do programa.
   - O `SCS`, sendo mais leve, tem um consumo de memória menor e, portanto, pode ser mais rápido.

### 5. **Tempo de Atualização da Interface**
   - No `AppGUI`, o tempo de execução medido inclui o tempo gasto para atualizar a interface gráfica (por exemplo, atualizar um `JTextArea` ou `JLabel`).
   - No `SCS`, o tempo de execução é medido apenas para o cálculo da LCS e SCS, sem incluir o tempo de atualização da interface.

---

## Como Mitigar as Divergências

Se você deseja comparar os tempos de execução de forma mais justa, siga estas recomendações:

### 1. **Medir Apenas o Tempo de Cálculo**
   - No `AppGUI`, meça apenas o tempo gasto para calcular a LCS e a SCS, excluindo o tempo gasto para atualizar a interface gráfica.
   - Exemplo:
     ```java
     long startTime = System.nanoTime();
     String lcs = lcs(fruit1, fruit2);
     long endTime = System.nanoTime();
     long executionTimeLCS = endTime - startTime;
     ```

### 2. **Executar Cálculos em uma Thread Separada**
   - Use `SwingWorker` ou uma `Thread` separada para executar os cálculos de LCS e SCS fora da EDT. Isso evita que a interface gráfica congele e reduz o impacto no tempo de execução.
   - Exemplo com `SwingWorker`:
     ```java
     new SwingWorker<Void, Void>() {
         @Override
         protected Void doInBackground() throws Exception {
             // Cálculos aqui
             return null;
         }
     }.execute();
     ```

### 3. **Testar com Entradas Grandes**
   - Para reduzir o impacto relativo do overhead da interface gráfica, teste com strings grandes (por exemplo, com centenas ou milhares de caracteres).

---

## Conclusão

As divergências nos tempos de execução entre a interface gráfica e o terminal são causadas principalmente pelo overhead da interface gráfica. A lógica de cálculo da LCS e SCS, no entanto, é a mesma nas duas versões. Se você estiver satisfeito com a lógica e não se preocupar com pequenas diferenças no tempo de execução, o código está pronto para uso.

Para mais detalhes, consulte o código-fonte ou entre em contato.