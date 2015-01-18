package br.com.vinyanalista.ag.rainhas;

import br.com.vinyanalista.ag.*;

public class ProblemaDasRainhas extends AlgoritmoGenetico<Tabuleiro> {

	private final int maximoDeIteracoes;
	private final Populacao<Tabuleiro> populacaoInicial;

	public ProblemaDasRainhas(int tamanhoDoTabuleiro, int maximoDeIteracoes) {
		// TODO Auto-generated constructor stub
		this.maximoDeIteracoes = maximoDeIteracoes;
		populacaoInicial = null;
	}

	@Override
	protected int fitnessDesejado() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected double probabilidadeDeMutacao() {
		// TODO Auto-generated method stub
		return 0;
	}

	public Tabuleiro resolver() {
		return executar(populacaoInicial, maximoDeIteracoes);
	}

	public static void main(String[] args) {
		ProblemaDasRainhas problema = new ProblemaDasRainhas(8, 100);
		Tabuleiro solucao = problema.resolver();
		System.out.println(solucao);
	}
}