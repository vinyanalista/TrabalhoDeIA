package br.com.vinyanalista.ag;

import java.util.*;

public class Populacao<Individuo extends DefinicaoDoIndividuo<Individuo>> {
	
	private final Random geradorDeNumerosAleatorios;
	private final List<Individuo> listaDeIndividuos;
	private final HashMap<Individuo, Double> probabilidades;
	
	public Populacao() {
		geradorDeNumerosAleatorios = new Random();
		listaDeIndividuos = new ArrayList<Individuo>();
		probabilidades = new HashMap<Individuo,Double>();
	}
	
	public void adicionar(Individuo individuo) {
		listaDeIndividuos.add(individuo);
	}
	
	public void calcularProbabilidades() {
		int fitnessTotal = 0;
		for (Individuo individuo : listaDeIndividuos) {
			fitnessTotal += individuo.fitness();
		}
		double probabilidadeAcumulada = 0;
		for (Individuo individuo : listaDeIndividuos) {
			double probabilidadeDoIndividuo = individuo.fitness() / (double) fitnessTotal;
			probabilidadeAcumulada += probabilidadeDoIndividuo;
			probabilidades.put(individuo, probabilidadeAcumulada); 
		}
	}
	
	public Individuo individuoAleatorio() {
		Individuo individuoEscolhido = null;
		do {
			double probabilidadeAleatoria = geradorDeNumerosAleatorios.nextDouble();
			for (Individuo individuo : listaDeIndividuos) {
				double probabilidadeDoIndividuo = probabilidades.get(individuo);
				if (probabilidadeDoIndividuo >= probabilidadeAleatoria) {
					individuoEscolhido = individuo;
					break;
				}
			}
		} while (individuoEscolhido == null);
		return individuoEscolhido;
	}
	
	public Individuo melhorIndividuo() {
		Individuo melhor = listaDeIndividuos.get(0);
		for (int i = 1; i < listaDeIndividuos.size(); i++) {
			Individuo proximoIndividuo = listaDeIndividuos.get(i);
			if (proximoIndividuo.fitness() > melhor.fitness()) {
				melhor = proximoIndividuo;
			}
		}
		return melhor;
	}
	
	public int tamanho() {
		return listaDeIndividuos.size();
	}

}