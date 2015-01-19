package br.com.vinyanalista.ag.rainhas;

import java.util.*;

import br.com.vinyanalista.ag.DefinicaoDoIndividuo;

public class Tabuleiro implements DefinicaoDoIndividuo<Tabuleiro> {
	
	private Integer fitness;
	private final int[] posicoesDasRainhasNasColunas;
	private final Integer quantidadeDeParesDeRainhas;
	private final int tamanhoDoTabuleiro;
	
	public Tabuleiro(int tamanhoDoTabuleiro) {
		this(tamanhoDoTabuleiro, true);
	}
	
	public Tabuleiro(int tamanhoDoTabuleiro, boolean gerarTabuleiroAleatorio) {
		posicoesDasRainhasNasColunas = new int[tamanhoDoTabuleiro];
		if (gerarTabuleiroAleatorio) {
			// Gera um tabuleiro aleatório com o tamanho especificado
			Random geradorDeNumerosAleatorios = new Random();
			for (int coluna = 0; coluna < tamanhoDoTabuleiro; coluna++) {
				int linhaAleatoria = geradorDeNumerosAleatorios.nextInt(tamanhoDoTabuleiro);
				setRainha(coluna, linhaAleatoria);
			}
		}
		quantidadeDeParesDeRainhas = combinacao(tamanhoDoTabuleiro, 2); 
		this.tamanhoDoTabuleiro = tamanhoDoTabuleiro;
	}
	
	private static final boolean ataca(int linhaDaRainha1, int linhaDaRainha2,
			int colunaDaRainha1, int colunaDaRainha2) {
		if (linhaDaRainha1 == linhaDaRainha2) return true;
		if (linhaDaRainha1 + colunaDaRainha2 - colunaDaRainha1 == linhaDaRainha2) return true;
		if (linhaDaRainha1 - colunaDaRainha2 + colunaDaRainha1 == linhaDaRainha2) return true;
		return false;
	}
	
	public static final int combinacao(int n, int p) {
		// http://gestaoemti.com.br/index.php/arranjo-combinao-e-permutao/
		return fatorial(n) / (fatorial(p) * fatorial(n - p));
	}

	@Override
	public Tabuleiro cruzar(Tabuleiro outroTabuleiro) {
		Random geradorDeNumerosAleatorios = new Random();
		int pontoDeCorte = geradorDeNumerosAleatorios.nextInt(tamanhoDoTabuleiro);
		Tabuleiro filho = new Tabuleiro(tamanhoDoTabuleiro, false);
		for (int coluna = 0; coluna < pontoDeCorte; coluna++) {
			int linha = this.getLinhaDaRainha(coluna);
			filho.setRainha(coluna, linha);
		}
		for (int coluna = pontoDeCorte; coluna < tamanhoDoTabuleiro; coluna++) {
			int linha = outroTabuleiro.getLinhaDaRainha(coluna);
			filho.setRainha(coluna, linha);
		}
		return filho;
	}
	
	@Override
	public boolean equals(Object outroObjeto) {
		if (!(outroObjeto instanceof Tabuleiro)) {
			return false;
		}
		Tabuleiro outroTabuleiro = (Tabuleiro) outroObjeto;
		return (tamanhoDoTabuleiro == outroTabuleiro.tamanhoDoTabuleiro)
				&& (posicoesDasRainhasNasColunas
						.equals(outroTabuleiro.posicoesDasRainhasNasColunas));
	}
	
	public static final int fatorial(int n) {
		// http://www.devmedia.com.br/calculando-fatorial-em-java/14273
		int f = n;
		while (n > 1) {
			f = f * (n - 1);
			n--;
		}
		return f;
	}

	@Override
	public int fitness() {
		// A função fitness para esse problema é a quantidade de pares de
		// rainhas que não se atacam
		if (fitness == null) {
			int quantidadeDeParesDeRainhasQueSeAtacam = 0;
			for (int colunaDaRainha1 = 0; colunaDaRainha1 < tamanhoDoTabuleiro - 1; colunaDaRainha1++) {
				int linhaDaRainha1 = getLinhaDaRainha(colunaDaRainha1);
				for (int colunaDaRainha2 = colunaDaRainha1 + 1; colunaDaRainha2 < tamanhoDoTabuleiro; colunaDaRainha2++) {
					int linhaDaRainha2 = getLinhaDaRainha(colunaDaRainha2);
					if (ataca(linhaDaRainha1, linhaDaRainha2, colunaDaRainha1,
							colunaDaRainha2))
						quantidadeDeParesDeRainhasQueSeAtacam++;
				}
			}
			fitness = quantidadeDeParesDeRainhas - quantidadeDeParesDeRainhasQueSeAtacam;
		}
		return fitness;
	}
	
	@Override
	public int fitnessDesejado() {
		// Deseja-se que nenhuma das rainhas seja capaz de atacar uma a outra.
		// Portanto, o valor desejado para a função fitness é a quantidade de
		// pares de rainhas (por exemplo, 28, para um tabuleiro 8x8).
		return quantidadeDeParesDeRainhas;
	}
	
	private int getLinhaDaRainha(int coluna) {
		return posicoesDasRainhasNasColunas[coluna];
	}

	@Override
	public void mutar() {
		Random geradorDeNumerosAleatorios = new Random();
		boolean houveMudanca = false;
		do {
			int colunaAleatoria = geradorDeNumerosAleatorios.nextInt(tamanhoDoTabuleiro);
			int linhaOriginal = getLinhaDaRainha(colunaAleatoria);
			int linhaNova = geradorDeNumerosAleatorios.nextInt(tamanhoDoTabuleiro);
			if (linhaNova != linhaOriginal) {
				setRainha(colunaAleatoria, linhaNova);
				houveMudanca = true;
			}
		} while (!houveMudanca);
	}
	
	private void setRainha(int coluna, int linha) {
		posicoesDasRainhasNasColunas[coluna] = linha;
	}
	
	@Override
	public String toString() {
		String[][] tabuleiroComoMatrizDeStrings = new String[tamanhoDoTabuleiro][tamanhoDoTabuleiro];
		for (int coluna = 0; coluna < tamanhoDoTabuleiro; coluna++) {
			for (int linha = 0; linha < tamanhoDoTabuleiro; linha++) {
				tabuleiroComoMatrizDeStrings[linha][coluna] = "-";
			}
		}
		for (int coluna = 0; coluna < tamanhoDoTabuleiro; coluna++) {
			int linha = getLinhaDaRainha(coluna);
			tabuleiroComoMatrizDeStrings[linha][coluna] = "X";
		}
		StringBuilder tabuleiroComoString = new StringBuilder();
		for (int linha = 0; linha < tamanhoDoTabuleiro; linha++) {
			for (int coluna = 0; coluna < tamanhoDoTabuleiro; coluna++) {
				tabuleiroComoString.append(tabuleiroComoMatrizDeStrings[linha][coluna]);
			}
			tabuleiroComoString.append("\n");
		}
		return tabuleiroComoString.toString();
	}

}