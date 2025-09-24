# Batalha Naval - Kotlin + JavaFX

## Descrição

Projeto de **Batalha Naval** desenvolvido em **Kotlin** usando **JavaFX** para a interface gráfica.  
O jogo permite que o usuário jogue contra o computador em um tabuleiro, atacando posições e tentando destruir todos os navios.

Este projeto foi desenvolvido como prática de programação orientada a objetos, lógica de jogos e manipulação de interface gráfica.

---

## Funcionalidades

- Menu inicial com escolha de:
  - Dificuldade (`Fácil`, `Intermediário`, `Difícil`)
  - Tamanho do tabuleiro (de 5x5 até 15x15)
- Tabuleiro gráfico com células clicáveis
- Sprites para:
  - Navios
  - Acertos
  - Água
- Sistema de pontuação
- Histórico de jogadas
- Mensagens de vitória ou derrota
- Opção de reiniciar o jogo ou voltar ao menu

---

## Estrutura do Projeto

```text
BatalhaNaval/
├─ src/main/kotlin/com/example/batalhanavaljfx/
│  ├─ BatalhaNaval.kt      # Classe principal
│  ├─ JogoController.kt    # Lógica do jogo
│  ├─ MenuController.kt    # Controle do menu
├─ src/main/resources/
│  ├─ com/example/batalhanavaljfx/
│  │  ├─ menu-view.fxml
│  │  ├─ jogo-view.fxml
│  │  └─ sprites/
│  │     ├─ ship.png
│  │     ├─ ship-hit.png
│  │     └─ water.png
│  ├─ batalha-naval.png
│  └─ styles.css
```

# Tecnologias Utilizadas

- **Kotlin 1.9+**
- **JavaFX 20+**
- **Maven**
- **FXML** para construção da interface
- **CSS** para estilização da interface

---

# Como Rodar

1. Clonar o repositório:

   ```bash
   git clone https://github.com/2sem-tec-prog/batalha-naval-jfx.git

2. Abrir no **IntelliJ IDEA** (ou outra IDE compatível com **Kotlin** e **JavaFX**).  

3. Configurar dependências do **JavaFX** (se necessário via Maven/Gradle).  

4. Executar a classe principal:  

```kotlin
fun main() {
    Application.launch(BatalhaNaval::class.java)
}
```

O menu será aberto. Escolha a dificuldade e o tamanho do tabuleiro e clique em **Jogar**.

## Estrutura do Jogo

- **Tabuleiro:** matriz de caracteres (`Array<Array<Char>>`)
  - `'P'` = Porta-aviões  
  - `'C'` = Cruzador  
  - `'R'` = Rebocador  
  - `' '` = Água  

- **Controle de jogadas:** cada célula é um `Button` dentro de um `GridPane`.

- **Acertos/Erros:** alteram sprites e atualizam pontuação.

- **Finalização do jogo:** alerta com resumo e opção de reiniciar ou voltar ao menu.


Desenvolvido por **Estevão Santos**, **Gustavo Decker**, **Giovane Melo** E **Gustavo Gonsalves**

