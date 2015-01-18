package br.com.vinyanalista.firefly;

public interface Vagalume {

	/**
	 * Calcula a atratividade do vagalume. Esse c�lculo � realizado no in�cio do
	 * algoritmo, quando a atratividade de toda a popula��o de vagalumes �
	 * calculada, e cada vez que o vagalume se mover em dire��o a outro.
	 */
	public void calcularAtratividade();

	/**
	 * Retorna a atratividade do vagalume, previamente calculada.
	 * 
	 * @return a atratividade do vagalume
	 */
	public double getAtratividade();

	/**
	 * Realiza o movimento desse vagalume em dire��o a outro fornecido como
	 * argumento. S�o passados como argumentos tamb�m os par�metros do Algoritmo
	 * do Vagalume, que s�o usados no c�lculo desse deslocamento.
	 * 
	 * @param v
	 *            o vagalume que � destino do movimento
	 * @param atratividadeInicial
	 *            par�metro do Algoritmo do Vagalume
	 * @param coeficienteDeAbsorcaoDeLuz
	 *            par�metro do Algoritmo do Vagalume
	 * @param parametroDeRandomizacao
	 *            par�metro do Algoritmo do Vagalume
	 */
	public void moverEmDirecaoA(Vagalume v, double atratividadeInicial,
			double coeficienteDeAbsorcaoDeLuz, double parametroDeRandomizacao);

	/**
	 * Retorna verdadeiro se o vagalume representa uma solu��o �tima para o
	 * problema.
	 */
	public boolean solucaoOtima();
}