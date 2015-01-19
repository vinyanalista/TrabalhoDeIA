package br.com.vinyanalista.ag.rainhas;

import br.com.vinyanalista.ag.*;

public class ProblemaDasRainhas extends AlgoritmoGenetico<Tabuleiro> {
	
	private static final int MAXIMO_DE_ITERACOES = 1000;
	private static final int TAMANHO_DA_POPULACAO_INICIAL = 500;
	private static final int TAMANHO_DO_TABULEIRO = 8;

	private final int maximoDeIteracoes;
	private final Populacao<Tabuleiro> populacaoInicial;

	public ProblemaDasRainhas() {
		this(TAMANHO_DO_TABULEIRO, MAXIMO_DE_ITERACOES, TAMANHO_DA_POPULACAO_INICIAL);
	}
	
	public ProblemaDasRainhas(int tamanhoDoTabuleiro) {
		this(tamanhoDoTabuleiro, MAXIMO_DE_ITERACOES, TAMANHO_DA_POPULACAO_INICIAL);
	}
	
	public ProblemaDasRainhas(int tamanhoDoTabuleiro, int maximoDeIteracoes) {
		this(tamanhoDoTabuleiro, maximoDeIteracoes, TAMANHO_DA_POPULACAO_INICIAL);
	}
	
	public ProblemaDasRainhas(int tamanhoDoTabuleiro, int maximoDeIteracoes, int tamanhoDaPopulacaoInicial) {
		this.maximoDeIteracoes = maximoDeIteracoes;
		populacaoInicial = new Populacao<Tabuleiro>();
		for (int t = 0; t < tamanhoDaPopulacaoInicial; t++) {
			Tabuleiro tabuleiro = new Tabuleiro(tamanhoDoTabuleiro);
			populacaoInicial.adicionar(tabuleiro);
		}
	}

	@Override
	protected double probabilidadeDeMutacao() {
		// A probabilidade de uma mutação ocorrer após um cruzamento é de 1%
		return 0.01;
	}

	public Tabuleiro resolver() {
		return executar(populacaoInicial, maximoDeIteracoes);
	}

	public static void main(String[] args) {
		ProblemaDasRainhas problema = new ProblemaDasRainhas();
		Tabuleiro solucao = problema.resolver();
		System.out.println(solucao);
	}
}