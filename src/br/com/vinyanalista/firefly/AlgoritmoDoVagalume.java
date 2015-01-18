package br.com.vinyanalista.firefly;

public class AlgoritmoDoVagalume {
	public static boolean DEBUG = false; // Define se mensagens explicativas
											// devem ser exibidas no console

	/**
	 * Exibe uma mensagem explicativa no console.
	 * 
	 * @param mensagem
	 *            mensagem a ser exibida no console.
	 */
	public static void log(String mensagem) {
		if (DEBUG)
			System.out.println(mensagem);
	}

	/**
	 * Gera um inteiro aleatório no intervalo definido.
	 * 
	 * @param minimo
	 *            limite inferior (incluso) do intervalo
	 * @param maximo
	 *            limite superior (incluso) do intervalo
	 * @return um inteiro escolhido aleatoriamente no intervalo definido
	 */
	public static int numeroAleatorio(int minimo, int maximo) {
		// http://stackoverflow.com/questions/363681/generating-random-number-in-a-range-with-java
		return minimo + (int) (Math.random() * ((maximo - minimo) + 1));
	}

	/**
	 * Gera um número decimal aleatório no intervalo definido.
	 * 
	 * @param minimo
	 *            limite inferior (incluso) do intervalo
	 * @param maximo
	 *            limite superior (incluso) do intervalo
	 * @return um número decimal escolhido aleatoriamente no intervalo definido
	 */
	public static double numeroAleatorio(double minimo, double maximo) {
		return minimo + (Math.random() * ((maximo - minimo) + 1));
	}

	// Parâmetros do Algoritmo do Vagalume
	private int quantidadeDeVagalumes;
	private int maxIteracoes;
	private double atratividadeInicial;
	private double coeficienteDeAbsorcaoDeLuz;
	private double parametroDeRandomizacao;
	private Vagalume[] vagalumes; // População de vagalumes

	/**
	 * Cria uma nova instância do Algoritmo do Vagalume.
	 * 
	 * @param populacao
	 *            população de vagalumes utilizada para procurar uma solução
	 *            para o problema.
	 * @param maxIteracoes
	 *            parâmetro do Algoritmo do Vagalume
	 * @param atratividadeInicial
	 *            parâmetro do Algoritmo do Vagalume
	 * @param coeficienteDeAbsorcaoDeLuz
	 *            parâmetro do Algoritmo do Vagalume
	 * @param parametroDeRandomizacao
	 *            parâmetro do Algoritmo do Vagalume
	 */
	public AlgoritmoDoVagalume(Vagalume[] populacao, int maxIteracoes,
			double atratividadeInicial, double coeficienteDeAbsorcaoDeLuz,
			double parametroDeRandomizacao) {
		vagalumes = populacao;
		quantidadeDeVagalumes = populacao.length;
		this.maxIteracoes = maxIteracoes;
		this.atratividadeInicial = atratividadeInicial;
		this.coeficienteDeAbsorcaoDeLuz = coeficienteDeAbsorcaoDeLuz;
		this.parametroDeRandomizacao = parametroDeRandomizacao;
		for (int v = 0; v < quantidadeDeVagalumes; v++)
			vagalumes[v].calcularAtratividade();
	}

	/**
	 * Executa o Algoritmo do Vagalume, retornando a melhor solução encontrada
	 * para o problema.
	 * 
	 * @return a melhor solução encontrada para o problema
	 */
	public Vagalume executar() {
		Vagalume melhorSolucao = vagalumes[0];
		for (int i = 0; i < maxIteracoes; i++) {
			log("Iteração" + i);
			for (int m = 0; m < quantidadeDeVagalumes; m++) {
				for (int u = 0; u < quantidadeDeVagalumes; u++) {
					if (vagalumes[m].getAtratividade() < vagalumes[u]
							.getAtratividade()) {
						vagalumes[m].moverEmDirecaoA(vagalumes[u],
								atratividadeInicial,
								coeficienteDeAbsorcaoDeLuz,
								parametroDeRandomizacao);
						vagalumes[m].calcularAtratividade();
						if (vagalumes[m].getAtratividade() > melhorSolucao
								.getAtratividade()) {
							melhorSolucao = vagalumes[m];
							break;
						}
					}
				}
			}
			log(String.valueOf(melhorSolucao.getAtratividade()));
			if (melhorSolucao.solucaoOtima())
				break;
		}
		return melhorSolucao;
	}

}
