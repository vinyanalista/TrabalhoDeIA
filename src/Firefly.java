import java.util.Random;

public class Firefly {

	static class PosicaoNoTabuleiro {
		int x, y;

		public PosicaoNoTabuleiro(int x, int y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}

	static int TAMANHO_DO_TABULEIRO, DIMENSOES, NUMERO_DE_SIMULACOES;
	static PosicaoNoTabuleiro[] POSICOES_INICIAIS_POSSIVEIS;

	static int NUMERO_DE_VAGALUMES, MAX_ITERACOES;
	static double ATRATIVIDADE_INICIAL, COEFICIENTE_DE_ABSORCAO_DE_LUZ,
			PARAMETRO_DE_RANDOMIZACAO;

	static PosicaoNoTabuleiro POSICAO_INICIAL;
	static Firefly MELHOR_SOLUCAO = null;

	static Firefly[] VAGALUMES;

	static void criarPopulacao(PosicaoNoTabuleiro posicaoInicial) {
		VAGALUMES = new Firefly[NUMERO_DE_VAGALUMES];
		for (int n = 0; n < NUMERO_DE_VAGALUMES; n++)
			VAGALUMES[n] = new Firefly(posicaoInicial);
	}

	static double distanciaEuclidiana(Firefly f1, Firefly f2) {
		double soma = 0;
		for (int d = 0; d < DIMENSOES; d++)
			soma += Math.pow((f1.posicao[d] - f2.posicao[d]), 2);
		return soma;
	}

	static Random geradorDeNumerosAleatorios = new Random();

	static int numeroAleatorio(int minimo, int maximo) {
		// http://stackoverflow.com/questions/363681/generating-random-number-in-a-range-with-java
		return minimo + (int) (Math.random() * ((maximo - minimo) + 1));
	}

	static double numeroAleatorio(double minimo, double maximo) {
		// http://stackoverflow.com/questions/363681/generating-random-number-in-a-range-with-java
		return minimo + (Math.random() * ((maximo - minimo) + 1));
	}

	// //////////////////IMPLEMENTAÇÃO DOS VAGALUMES///////////////////////////

	private int atratividade;
	PosicaoNoTabuleiro posicaoInicial; // Posicao inicial do cavalo
	private int[] posicao; // Sequencia de movimentos efetuados pelo cavalo

	public Firefly(PosicaoNoTabuleiro posicaoInicial) {
		this.posicaoInicial = posicaoInicial;
		posicao = new int[DIMENSOES];
		for (int d = 0; d < DIMENSOES; d++)
			posicao[d] = numeroAleatorio(0, 7);
		calcularAtratividade();
	}
	
	private PosicaoNoTabuleiro novaPosicao(PosicaoNoTabuleiro posicaoAnterior, int movimento) {
		PosicaoNoTabuleiro pos = new PosicaoNoTabuleiro(posicaoAnterior.x,
				posicaoAnterior.y);
		switch (movimento) {
		case 0:
			pos.x = pos.x + 1;
			pos.y = pos.y + 2;
			break;
		case 1:
			pos.x = pos.x + 2;
			pos.y = pos.y + 1;
			break;
		case 2:
			pos.x = pos.x + 2;
			pos.y = pos.y - 1;
			break;
		case 3:
			pos.x = pos.x + 1;
			pos.y = pos.y - 2;
			break;
		case 4:
			pos.x = pos.x - 1;
			pos.y = pos.y - 2;
			break;
		case 5:
			pos.x = pos.x - 2;
			pos.y = pos.y - 1;
			break;
		case 6:
			pos.x = pos.x - 2;
			pos.y = pos.y + 1;
			break;
		case 7:
			pos.x = pos.x - 1;
			pos.y = pos.y + 2;
			break;
		}
		return pos;
	}

	private void calcularAtratividade() {
		PosicaoNoTabuleiro pos = POSICAO_INICIAL;
		int movimentosLegais = 0;
		for (int d = 0; d < DIMENSOES; d++) {
			pos = novaPosicao(pos, posicao[d]);
			if ((pos.x >= 1) && (pos.x <= TAMANHO_DO_TABULEIRO) && (pos.y >= 1)
					&& (pos.y <= TAMANHO_DO_TABULEIRO))
				movimentosLegais++;
			else
				break;
		}
		/*
		 * A função objetivo é definida em termos de movimentos legais efetuados
		 * pelo cavalo
		 */
		//atratividade = movimentosLegais;
		
		boolean[][] tabuleiro = new boolean[TAMANHO_DO_TABULEIRO][TAMANHO_DO_TABULEIRO];
		tabuleiro[posicaoInicial.x - 1][posicaoInicial.y - 1] = true;
		pos = POSICAO_INICIAL;
		int casasUnicasVisitadas = 1;
		for (int m = 0; m < movimentosLegais; m++) {
			pos = novaPosicao(pos, posicao[m]);
			if (!tabuleiro[pos.x - 1][pos.y - 1]) {
				tabuleiro[pos.x - 1][pos.y - 1] = true;
				casasUnicasVisitadas++;
			} else
				break;
		}
		atratividade = casasUnicasVisitadas;
	}

	/*
	 * public void setIntensidadeDaLuz(double intensidadeDaLuz) {
	 * this.intensidadeDaLuz = intensidadeDaLuz; }
	 */

	// public double getIntensidadeDaLuz() {
	// return 1 / atratividade();
	// }

	public void moverEmDirecaoA(Firefly u) {
		Firefly m = this;
		double r = distanciaEuclidiana(m, u);
		double[] vetorDeNumerosAleatorios = new double[DIMENSOES];
		for (int v = 0; v < vetorDeNumerosAleatorios.length; v++)
			vetorDeNumerosAleatorios[v] = numeroAleatorio(0f, 1f);
		for (int d = 0; d < DIMENSOES; d++) {
			posicao[d] = (int) Math
					.floor(posicao[d]
							+ (ATRATIVIDADE_INICIAL
									* Math.exp(-1
											* COEFICIENTE_DE_ABSORCAO_DE_LUZ
											* r) * (posicao[d] - u.posicao[d]))
							+ (PARAMETRO_DE_RANDOMIZACAO * vetorDeNumerosAleatorios[d]));
			posicao[d] = posicao[d] % TAMANHO_DO_TABULEIRO; // Prevenção para
															// movimento
															// inválido
		}
		calcularAtratividade();
	}

	public static void main(String[] args) {
		// Configurações específicas do problema
		TAMANHO_DO_TABULEIRO = 8;

		POSICOES_INICIAIS_POSSIVEIS = new PosicaoNoTabuleiro[16];
		POSICOES_INICIAIS_POSSIVEIS[0] = new PosicaoNoTabuleiro(1, 1);
		POSICOES_INICIAIS_POSSIVEIS[1] = new PosicaoNoTabuleiro(2, 1);
		POSICOES_INICIAIS_POSSIVEIS[2] = new PosicaoNoTabuleiro(3, 1);
		POSICOES_INICIAIS_POSSIVEIS[3] = new PosicaoNoTabuleiro(4, 1);
		POSICOES_INICIAIS_POSSIVEIS[4] = new PosicaoNoTabuleiro(1, 2);
		POSICOES_INICIAIS_POSSIVEIS[5] = new PosicaoNoTabuleiro(2, 2);
		POSICOES_INICIAIS_POSSIVEIS[6] = new PosicaoNoTabuleiro(3, 2);
		POSICOES_INICIAIS_POSSIVEIS[7] = new PosicaoNoTabuleiro(4, 2);
		POSICOES_INICIAIS_POSSIVEIS[8] = new PosicaoNoTabuleiro(1, 3);
		POSICOES_INICIAIS_POSSIVEIS[9] = new PosicaoNoTabuleiro(2, 3);
		POSICOES_INICIAIS_POSSIVEIS[10] = new PosicaoNoTabuleiro(3, 3);
		POSICOES_INICIAIS_POSSIVEIS[11] = new PosicaoNoTabuleiro(4, 3);
		POSICOES_INICIAIS_POSSIVEIS[12] = new PosicaoNoTabuleiro(1, 4);
		POSICOES_INICIAIS_POSSIVEIS[13] = new PosicaoNoTabuleiro(2, 4);
		POSICOES_INICIAIS_POSSIVEIS[14] = new PosicaoNoTabuleiro(3, 4);
		POSICOES_INICIAIS_POSSIVEIS[15] = new PosicaoNoTabuleiro(4, 4);

		DIMENSOES = TAMANHO_DO_TABULEIRO * TAMANHO_DO_TABULEIRO;
		NUMERO_DE_SIMULACOES = 20;

		// Parametros do Firefly Algorithm
		NUMERO_DE_VAGALUMES = 500;
		MAX_ITERACOES = 5000;
		ATRATIVIDADE_INICIAL = 1;
		COEFICIENTE_DE_ABSORCAO_DE_LUZ = 1;
		PARAMETRO_DE_RANDOMIZACAO = 1;

		Firefly melhorSolucao = null;

		// Procura uma solução para cada posição inicial possível
		for (int p = 0; p < POSICOES_INICIAIS_POSSIVEIS.length; p++) {
			POSICAO_INICIAL = POSICOES_INICIAIS_POSSIVEIS[p];
			System.out.println("Verificando a posição inicial "
					+ POSICAO_INICIAL);
			// Realiza várias simulações para melhorar a qualidade da resposta
			for (int s = 0; s < NUMERO_DE_SIMULACOES; s++) {
				System.out.println("Simulação " + s);
				criarPopulacao(POSICAO_INICIAL);
				MELHOR_SOLUCAO = VAGALUMES[0];
				for (int i = 0; i < MAX_ITERACOES; i++) {
					System.out.print("Iteração " + i);
					for (int m = 0; m < NUMERO_DE_VAGALUMES; m++)
						for (int u = 0; u < NUMERO_DE_VAGALUMES; u++)
							if (VAGALUMES[m].atratividade < VAGALUMES[u].atratividade) {
								VAGALUMES[m].moverEmDirecaoA(VAGALUMES[u]);
								if (VAGALUMES[m].atratividade > MELHOR_SOLUCAO.atratividade) {
									MELHOR_SOLUCAO = VAGALUMES[m];
									break;
								}
							}
					System.out.println(" - " + MELHOR_SOLUCAO.atratividade);
					if (MELHOR_SOLUCAO.atratividade == DIMENSOES)
						break;
				}
				if ((melhorSolucao == null)
						|| (MELHOR_SOLUCAO.atratividade > melhorSolucao.atratividade))
					melhorSolucao = MELHOR_SOLUCAO;
				if (melhorSolucao.atratividade == DIMENSOES)
					break;
			}
			if (melhorSolucao.atratividade == DIMENSOES)
				break;
		}

		// Exibe o resultado final
		System.out.println();
		System.out.println("RESPOSTA");
		System.out.println("Posição inicial: " + melhorSolucao.posicaoInicial);
		for (int m = 0; m < DIMENSOES; m++)
			System.out.println(melhorSolucao.posicao[m]);

	}

}