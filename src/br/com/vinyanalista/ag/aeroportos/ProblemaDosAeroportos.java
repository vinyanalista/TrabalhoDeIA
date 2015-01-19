package br.com.vinyanalista.ag.aeroportos;

import br.com.vinyanalista.ag.*;

public class ProblemaDosAeroportos extends AlgoritmoGenetico<Mapa> {

	private static final int MAXIMO_DE_ITERACOES = 1000;
	private static final int TAMANHO_DA_POPULACAO_INICIAL = 4;
	private static final Cidade[] CIDADES;
	private static final int QUANTIDADE_DE_AEROPORTOS = 3;
	
	static {
		CIDADES = new Cidade[6];
		CIDADES[0] = new Cidade(0, 0);
		CIDADES[1] = new Cidade(1, 0);
		CIDADES[2] = new Cidade(3, 3);
		CIDADES[3] = new Cidade(4, 3);
		CIDADES[4] = new Cidade(6, 6);
		CIDADES[5] = new Cidade(7, 6);
	}

	private final int maximoDeIteracoes;
	private final Populacao<Mapa> populacaoInicial;

	public ProblemaDosAeroportos() {
		this(CIDADES, QUANTIDADE_DE_AEROPORTOS, MAXIMO_DE_ITERACOES, TAMANHO_DA_POPULACAO_INICIAL);
	}
	
	public ProblemaDosAeroportos(Cidade[] cidades, int quantidadeDeAeroportos) {
		this(cidades, quantidadeDeAeroportos, MAXIMO_DE_ITERACOES, TAMANHO_DA_POPULACAO_INICIAL);
	}
	
	public ProblemaDosAeroportos(Cidade[] cidades, int quantidadeDeAeroportos, int maximoDeIteracoes) {
		this(cidades, quantidadeDeAeroportos, maximoDeIteracoes, TAMANHO_DA_POPULACAO_INICIAL);
	}
	
	public ProblemaDosAeroportos(Cidade[] cidades, int quantidadeDeAeroportos, int maximoDeIteracoes, int tamanhoDaPopulacaoInicial) {
		this.maximoDeIteracoes = maximoDeIteracoes;
		
		populacaoInicial = new Populacao<Mapa>();
		for (int m = 0; m < tamanhoDaPopulacaoInicial; m++) {
			Mapa mapa = new Mapa(cidades, quantidadeDeAeroportos);
			populacaoInicial.adicionar(mapa);
		}
	}

	@Override
	protected double probabilidadeDeMutacao() {
		// A probabilidade de uma mutação ocorrer após um cruzamento é de 1%
		return 0.01;
	}
	
	private Mapa resolver() {
		return executar(populacaoInicial, maximoDeIteracoes);
	}
	
	public static void main(String[] args) {
		ProblemaDosAeroportos problema = new ProblemaDosAeroportos();
		Mapa solucao = problema.resolver();
		System.out.println(solucao);
	}

}