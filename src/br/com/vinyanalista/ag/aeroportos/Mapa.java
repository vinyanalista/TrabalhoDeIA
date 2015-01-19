package br.com.vinyanalista.ag.aeroportos;

import java.util.Random;

import br.com.vinyanalista.ag.DefinicaoDoIndividuo;

public class Mapa implements DefinicaoDoIndividuo<Mapa> {

	private final Cidade[] aeroportos;
	private final Cidade[] cidades;
	private Integer fitness;
	private final int piorSomaDasDistanciasImaginavel;
	private final int quantidadeDeAeroportos;

	public Mapa(Cidade[] cidades, int quantidadeDeAeroportos) {
		this(cidades, quantidadeDeAeroportos, true);
	}
	
	public Mapa(Cidade[] cidades, int quantidadeDeAeroportos, boolean gerarMapaAleatorio) {
		aeroportos = new Cidade[quantidadeDeAeroportos];
		this.cidades = cidades;
		piorSomaDasDistanciasImaginavel = piorSomaDasDistanciasImaginavel();
		this.quantidadeDeAeroportos = quantidadeDeAeroportos;
		if (gerarMapaAleatorio) {
			// Gera um mapa aleatório com as cidades e a quantidade de
			// aeroportos especificadas
			Random geradorDeNumerosAleatorios = new Random();
			for (int a = 0; a < quantidadeDeAeroportos; a++) {
				Cidade cidadeAleatoria;
				boolean cidadeJaEscolhida;
				do {
					cidadeJaEscolhida = false;
					int numeroAleatorio = geradorDeNumerosAleatorios.nextInt(cidades.length);
					cidadeAleatoria = cidades[numeroAleatorio];
					for (int b = 0; b < a; b++) {
						if (aeroportos[b].equals(cidadeAleatoria)) {
							cidadeJaEscolhida = true;
							break;
						}
					}	
				} while (cidadeJaEscolhida);
				aeroportos[a] = cidadeAleatoria;
			}
		}
		
	}
	
	@Override
	public Mapa cruzar(Mapa outroMapa) {
//		System.out.println("cruzar()"); // TODO Teste, remover
		Mapa filho;
		do {
			Random geradorDeNumerosAleatorios = new Random();
			int pontoDeCorte = geradorDeNumerosAleatorios.nextInt(quantidadeDeAeroportos);
			filho = new Mapa(cidades, quantidadeDeAeroportos, false);
			for (int a = 0; a < pontoDeCorte; a++) {
				Cidade aeroporto = aeroportos[a];
				filho.aeroportos[a] = aeroporto;
			}
			for (int a = pontoDeCorte; a < quantidadeDeAeroportos; a++) {
				Cidade aeroporto = outroMapa.aeroportos[a];
				filho.aeroportos[a] = aeroporto;
			}
		} while (!filho.valido());
		return filho;
	}
	
	private int distanciaParaAeroportoMaisProximo(Cidade cidade) {
		Cidade aeroportoMaisProximo = aeroportos[0];
		int menorDistancia = cidade.distancia(aeroportoMaisProximo);
		for (Cidade aeroporto : aeroportos) {
			int distancia = cidade.distancia(aeroporto);
			if (distancia < menorDistancia) {
				aeroportoMaisProximo = aeroporto;
				menorDistancia = distancia;
			}
		}
		return menorDistancia;
	}
	
	@Override
	public boolean equals(Object outroObjeto) {
//		System.out.println("equals()"); // TODO Teste, remover
		if (!(outroObjeto instanceof Mapa)) {
			return false;
		}
		Mapa outroMapa = (Mapa) outroObjeto;
		return (aeroportos.equals(outroMapa.aeroportos)) && (cidades.equals(outroMapa.cidades)); 
	}

	@Override
	public int fitness() {
//		System.out.println("fitness()"); // TODO Teste, remover
		if (fitness == null) {
			int somaDasDistancias = 0;
			for (Cidade cidade : cidades) {
				somaDasDistancias += distanciaParaAeroportoMaisProximo(cidade);
			}
			fitness = piorSomaDasDistanciasImaginavel - somaDasDistancias;
		}
		return fitness;
	}

	@Override
	public int fitnessDesejado() {
//		System.out.println("fitnessDesejado()"); // TODO Teste, remover
		return piorSomaDasDistanciasImaginavel;
	}
	
	private int maiorX() {
		Cidade cidade = cidades[0];
		int maiorX = cidade.getX();
		for (int c = 1; c < cidades.length; c++) {
			cidade = cidades[c];
			if (cidade.getX() > maiorX) {
				maiorX = cidade.getX();
			}
		}
		return maiorX;
	}
	
	private int maiorY() {
		Cidade cidade = cidades[0];
		int maiorY = cidade.getY();
		for (int c = 1; c < cidades.length; c++) {
			cidade = cidades[c];
			if (cidade.getY() > maiorY) {
				maiorY = cidade.getY();
			}
		}
		return maiorY;
	}
	
	private int menorX() {
		Cidade cidade = cidades[0];
		int menorX = cidade.getX();
		for (int c = 1; c < cidades.length; c++) {
			cidade = cidades[c];
			if (cidade.getX() < menorX) {
				menorX = cidade.getX();
			}
		}
		return menorX;
	}
	
	private int menorY() {
		Cidade cidade = cidades[0];
		int menorY = cidade.getY();
		for (int c = 1; c < cidades.length; c++) {
			cidade = cidades[c];
			if (cidade.getY() < menorY) {
				menorY = cidade.getY();
			}
		}
		return menorY;
	}

	@Override
	public void mutar() {
//		System.out.println("mutar()"); // TODO Teste, remover
		Random geradorDeNumerosAleatorios = new Random();
		boolean houveMudanca = false;
		do {
			int posicaoAleatoria = geradorDeNumerosAleatorios.nextInt(quantidadeDeAeroportos);
			Cidade aeroportoOriginal = aeroportos[posicaoAleatoria];
			int numeroAleatorio = geradorDeNumerosAleatorios.nextInt(cidades.length);
			Cidade cidadeAleatoria = cidades[numeroAleatorio]; 
			if (cidadeAleatoria != aeroportoOriginal) {
				aeroportos[posicaoAleatoria] = cidadeAleatoria;
				if (valido()) {
					houveMudanca = true;
				} else {
					aeroportos[posicaoAleatoria] = aeroportoOriginal;
				}
			}
		} while (!houveMudanca);
	}
	
	private int piorSomaDasDistanciasImaginavel() {
		Cidade menorCidadeHipotetica = new Cidade(menorX(), menorY());
		Cidade maiorCidadeHipotetica = new Cidade(maiorX(), maiorY());
		int maiorDistanciaImaginavel = menorCidadeHipotetica.distancia(maiorCidadeHipotetica);
		return maiorDistanciaImaginavel * cidades.length;
	}
	
	@Override
	public String toString() {
//		System.out.println("toString()"); // TODO Teste, remover
		int maiorY = maiorY();
		int menorY = menorY();
		int menorX = menorX();
		int maiorX = maiorX();
		int amplitudeX = maiorX - menorX + 1;
		int amplitudeY = maiorY - menorY + 1;
		String[][] mapaComoArrayDeStrings = new String[amplitudeY][amplitudeX];
		for (int x = 0; x < amplitudeX; x++) {
			for (int y = 0; y < amplitudeY; y++) {
				mapaComoArrayDeStrings[y][x] = "-";
			}
		}
		for (Cidade cidade : cidades) {
			boolean eAeroporto = false;
			for (Cidade aeroporto : aeroportos) {
				if (cidade.equals(aeroporto)) {
					eAeroporto = true;
					break;
				}
			}
			String cidadeComoString = eAeroporto ? "A" : "C";
			int x = cidade.getX() - menorX;
			int y = cidade.getY() - menorY;
			mapaComoArrayDeStrings[y][x] = cidadeComoString;
		}
		StringBuilder mapaComoString = new StringBuilder();
		for (int y = amplitudeY - 1; y > -1; y--) {
			for (int x = 0; x < amplitudeX; x++) {
				mapaComoString.append(mapaComoArrayDeStrings[y][x]);
			}
			mapaComoString.append("\n");
		}
		return mapaComoString.toString();
	}
	
	private boolean valido() {
		// Verifica se o mapa é válido (será inválido quando uma cidade for
		// escolhida para ser aeroporto mais de uma vez)
		boolean valido = true;
		for (int a = 1; a < quantidadeDeAeroportos; a++) {
			for (int b = 0; b < a; b++) {
				if (aeroportos[a].equals(aeroportos[b])) {
					valido = false;
					break;
				}
			}
		}
		return valido;
	}

}