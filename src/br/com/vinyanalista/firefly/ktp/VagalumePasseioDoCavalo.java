package br.com.vinyanalista.firefly.ktp;

import br.com.vinyanalista.firefly.*;

public class VagalumePasseioDoCavalo implements Vagalume {

	/**
	 * Representa uma casa de um tabuleiro de xadrez N x N. Cada uma das
	 * coordenadas X e Y deve assumir um valor que vai de 1 at� N.
	 */
	private static class PosicaoNoTabuleiro {
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

	/**
	 * Cria uma popula��o inicial de vagalumes, que iniciam em determinada
	 * posi��o do tabuleiro e apresentam movimentos definidos aleatoriamente.
	 * 
	 * @return uma popula��o inicial de vagalumes
	 */
	private static VagalumePasseioDoCavalo[] criarPopulacao() {
		VagalumePasseioDoCavalo[] vagalumes = new VagalumePasseioDoCavalo[QUANTIDADE_DE_VAGALUMES];
		for (int n = 0; n < QUANTIDADE_DE_VAGALUMES; n++)
			vagalumes[n] = new VagalumePasseioDoCavalo();
		return vagalumes;
	}

	/**
	 * Calcula o quadrado da dist�ncia euclidiana entre dois vagalumes (realiza
	 * a soma dos quadrados das diferen�as entre as coordenadas, mas n�o calcula
	 * a raiz quadrada dessa soma).
	 * 
	 * @param v1
	 *            um vagalume
	 * @param v2
	 *            outro vagalume
	 * @return o quadrado da a dist�ncia euclidiana entre os dois vagalumes
	 *         fornecidos como argumento
	 */
	private static double distanciaEuclidiana(VagalumePasseioDoCavalo v1,
			VagalumePasseioDoCavalo v2) {
		double soma = 0;
		for (int d = 0; d < DIMENSOES; d++)
			soma += Math.pow((v1.posicao[d] - v2.posicao[d]), 2);
		return soma;
	}

	// Posi��o inicial no tabuleiro adotada para todos os vagalumes de uma
	// inst�ncia do problema
	private static PosicaoNoTabuleiro POSICAO_INICIAL;

	// Quantidade de vagalumes usados para procurar a resposta
	private static int QUANTIDADE_DE_VAGALUMES;

	// Tamanho do tabuleiro do problema
	private static int TAMANHO_DO_TABULEIRO = 8;

	// Cada dimens�o nesse problema representa o i-�simo movimento feito pelo
	// cavalo na sequ�ncia de movimentos para percorrer todas as casas do
	// tabuleiro
	private static int DIMENSOES = TAMANHO_DO_TABULEIRO * TAMANHO_DO_TABULEIRO;

	/*******************************************************************************/
	/************************* IMPLEMENTA��O DOS VAGALUMES *************************/
	/*******************************************************************************/

	// A atratividade de um vagalume � representada pela quantidade de
	// movimentos v�lidos que ele define para um cavalo
	private int atratividade;

	PosicaoNoTabuleiro posicaoInicial; // Posicao inicial do cavalo
	private int[] posicao; // Sequencia de movimentos efetuados pelo cavalo

	/**
	 * Cria um vagalume com uma sequ�ncia de movimentos aleat�rios.
	 */
	public VagalumePasseioDoCavalo() {
		this.posicaoInicial = POSICAO_INICIAL;
		posicao = new int[DIMENSOES];
		for (int d = 0; d < DIMENSOES; d++)
			posicao[d] = AlgoritmoDoVagalume.numeroAleatorio(0, 7);
	}

	/**
	 * Dados uma posi��o no tabuleiro e um movimento do cavalo (inteiro de 0 a
	 * 7), retorna a nova posi��o do cavalo ap�s realizar esse movimento. Vale
	 * observar que n�o necessariamente a posi��o retornada � v�lida.
	 * 
	 * @param posicaoAnterior
	 *            uma posi��o no tabuleiro
	 * @param movimento
	 *            um movimento do cavalo (inteiro de 0 a 7)
	 * @return a nova posi��o (n�o necessariamente v�lida) do cavalo ap�s
	 *         realizar esse movimento
	 */
	private PosicaoNoTabuleiro novaPosicao(PosicaoNoTabuleiro posicaoAnterior,
			int movimento) {
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

	/**
	 * Calcula a atratividade do vagalume com base na quantidade de movimentos
	 * v�lidos que ele define para um cavalo.
	 */
	@Override
	public void calcularAtratividade() {
		PosicaoNoTabuleiro pos = POSICAO_INICIAL;

		// Verifica quantos movimentos v�lidos o vagalume define para o cavalo
		int movimentosLegais = 0;
		for (int d = 0; d < DIMENSOES; d++) {
			pos = novaPosicao(pos, posicao[d]);
			if ((pos.x >= 1) && (pos.x <= TAMANHO_DO_TABULEIRO) && (pos.y >= 1)
					&& (pos.y <= TAMANHO_DO_TABULEIRO))
				movimentosLegais++;
			else
				break; // Para de incrementar a vari�vel quando encontra o
						// primeiro movimento inv�lido
		}
		// atratividade = movimentosLegais;

		// Verifica quantas casas diferentes o cavalo percorre
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

	/**
	 * Retorna a atratividade do vagalume. A atratividade deve ser previamente
	 * calculada invocando-se o m�todo calcularAtratividade().
	 */
	@Override
	public double getAtratividade() {
		return atratividade;
	}

	/**
	 * Realiza o movimento desse vagalume em dire��o a outro fornecido como
	 * argumento. S�o passados como argumentos tamb�m os par�metros do Algoritmo
	 * do Vagalume, que s�o usados no c�lculo desse deslocamento.
	 */
	@Override
	public void moverEmDirecaoA(Vagalume v, double atratividadeInicial,
			double coeficienteDeAbsorcaoDeLuz, double parametroDeRandomizacao) {
		VagalumePasseioDoCavalo m = this;
		VagalumePasseioDoCavalo u = (VagalumePasseioDoCavalo) v;
		double r = distanciaEuclidiana(m, u);
		double[] vetorDeNumerosAleatorios = new double[DIMENSOES];
		for (int n = 0; n < vetorDeNumerosAleatorios.length; n++)
			vetorDeNumerosAleatorios[n] = AlgoritmoDoVagalume.numeroAleatorio(
					0f, 1f);
		for (int d = 0; d < DIMENSOES; d++) {
			posicao[d] = (int) Math
					.floor(posicao[d]
							+ (atratividadeInicial
									* Math.exp(-1 * coeficienteDeAbsorcaoDeLuz
											* r) * (posicao[d] - u.posicao[d]))
							+ (parametroDeRandomizacao * vetorDeNumerosAleatorios[d]));
			posicao[d] = posicao[d] % TAMANHO_DO_TABULEIRO; // Preven��o para
															// movimento
															// inv�lido
		}

	}

	/**
	 * Retorna verdadeiro se o vagalume representa uma solu��o �tima para o
	 * problema. Isso acontece se sua sequ�ncia de movimentos faz com que o
	 * cavalo percorra todas as casas do tabuleiro.
	 */
	@Override
	public boolean solucaoOtima() {
		return (atratividade == DIMENSOES);
	}

	/**
	 * Resolve o problema do Passeio do Cavalo para um tabuleiro 8 x 8.
	 */
	public static void resolverProblemaParaTabuleiro8x8() {
		AlgoritmoDoVagalume.DEBUG = true;
		
		final PosicaoNoTabuleiro[] POSICOES_INICIAIS_POSSIVEIS = new PosicaoNoTabuleiro[16];

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

		final int NUMERO_DE_SIMULACOES = 20;

		// Par�metros do Algoritmo do Vagalume
		QUANTIDADE_DE_VAGALUMES = 500;
		final int MAX_ITERACOES = 5000;
		final int ATRATIVIDADE_INICIAL = 1;
		final int COEFICIENTE_DE_ABSORCAO_DE_LUZ = 1;
		final int PARAMETRO_DE_RANDOMIZACAO = 1;

		VagalumePasseioDoCavalo resposta = null;
		// Procura uma solu��o para cada posi��o inicial poss�vel
		for (int p = 0; p < POSICOES_INICIAIS_POSSIVEIS.length; p++) {
			POSICAO_INICIAL = POSICOES_INICIAIS_POSSIVEIS[p];
			AlgoritmoDoVagalume.log("Verificando a posi��o inicial "
					+ POSICAO_INICIAL);
			// Realiza v�rias simula��es para melhorar a qualidade da resposta
			for (int s = 0; s < NUMERO_DE_SIMULACOES; s++) {
				AlgoritmoDoVagalume.log("Simula��o " + s);
				VagalumePasseioDoCavalo solucao = (VagalumePasseioDoCavalo) new AlgoritmoDoVagalume(
						criarPopulacao(), MAX_ITERACOES, ATRATIVIDADE_INICIAL,
						COEFICIENTE_DE_ABSORCAO_DE_LUZ,
						PARAMETRO_DE_RANDOMIZACAO).executar();
				if ((resposta == null)
						|| (solucao.getAtratividade() > resposta
								.getAtratividade()))
					resposta = solucao;
				if (solucao.solucaoOtima())
					break;
			}
		}

		// Exibe o resultado final
		AlgoritmoDoVagalume.log("");
		AlgoritmoDoVagalume.log("RESPOSTA");
		AlgoritmoDoVagalume.log("Posi��o inicial: " + resposta.posicaoInicial);
		for (int m = 0; m < DIMENSOES; m++)
			AlgoritmoDoVagalume.log(String.valueOf(resposta.posicao[m]));
	}

	public static void main(String[] args) {
		resolverProblemaParaTabuleiro8x8();
	}

}
