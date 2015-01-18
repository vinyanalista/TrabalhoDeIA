package br.com.vinyanalista.firefly;

public interface Vagalume {

	/**
	 * Calcula a atratividade do vagalume. Esse cálculo é realizado no início do
	 * algoritmo, quando a atratividade de toda a população de vagalumes é
	 * calculada, e cada vez que o vagalume se mover em direção a outro.
	 */
	public void calcularAtratividade();

	/**
	 * Retorna a atratividade do vagalume, previamente calculada.
	 * 
	 * @return a atratividade do vagalume
	 */
	public double getAtratividade();

	/**
	 * Realiza o movimento desse vagalume em direção a outro fornecido como
	 * argumento. São passados como argumentos também os parâmetros do Algoritmo
	 * do Vagalume, que são usados no cálculo desse deslocamento.
	 * 
	 * @param v
	 *            o vagalume que é destino do movimento
	 * @param atratividadeInicial
	 *            parâmetro do Algoritmo do Vagalume
	 * @param coeficienteDeAbsorcaoDeLuz
	 *            parâmetro do Algoritmo do Vagalume
	 * @param parametroDeRandomizacao
	 *            parâmetro do Algoritmo do Vagalume
	 */
	public void moverEmDirecaoA(Vagalume v, double atratividadeInicial,
			double coeficienteDeAbsorcaoDeLuz, double parametroDeRandomizacao);

	/**
	 * Retorna verdadeiro se o vagalume representa uma solução ótima para o
	 * problema.
	 */
	public boolean solucaoOtima();
}