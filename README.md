# Jogo das Tendas em Kotlin

Este repositório contém a implementação em Kotlin do jogo das tendas, desenvolvido como parte de um projeto acadêmico. O objetivo do jogo é posicionar tendas em um terreno contendo árvores, seguindo regras específicas.

## Funcionalidades Implementadas

- **Terreno Dinâmico:** O terreno inicial agora mostra árvores espalhadas, variando de acordo com o tamanho do terreno, garantindo que cada jogo seja válido desde o início.
  
- **Atualização Dinâmica do Terreno:** O terreno é atualizado à medida que o jogador introduz as coordenadas das tendas. Isso ocorre em um ciclo de jogadas, onde o terreno é ajustado a cada iteração, terminando quando o jogador posiciona o número correto de tendas e respeita as regras do jogo.

- **Regras do Jogo Implementadas:** As regras do jogo são rigorosamente seguidas:
    - As tendas devem estar em quadrados adjacentes (horizontal ou vertical) a uma árvore.
    - As tendas não podem se tocar, nem mesmo na diagonal.
    - Os números fora da grelha indicam o número total de tendas em cada linha ou coluna.
      
- **Menu Interativo:** O programa apresenta um menu inicial e perguntas que conduzem ao jogo, mantendo a mesma estrutura da parte 1 do projeto. No entanto, foi adicionado um ciclo que só encerra quando o jogador escolhe a opção "Sair". Após completar um jogo, o programa retorna ao menu principal. Além disso, a qualquer momento, o jogador pode optar por desistir do jogo atual, voltando ao menu.




