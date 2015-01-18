package br.com.vinyanalista.ag;

import java.util.*;

public class Populacao<Individuo extends DefinicaoDoIndividuo<Individuo>> {
	
	private final Random geradorDeNumerosAleatorios;
	private List<Individuo> listaDeIndividuos;
	
	public Populacao() {
		geradorDeNumerosAleatorios = new Random();
		listaDeIndividuos = new ArrayList<Individuo>();
	}
	
	public void adicionar(Individuo individuo) {
		listaDeIndividuos.add(individuo);
	}
	
	public Individuo individuoAleatorio() {
		return listaDeIndividuos.get(numeroAleatorio(1, tamanho()));
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
	
	private int numeroAleatorio(int menorValor, int maiorValor) {
		long amplitude = (long) maiorValor - (long) menorValor + 1;
		long fracao = (long)(amplitude * geradorDeNumerosAleatorios.nextDouble());
		return (int)(fracao + menorValor);
	}
	
	public int tamanho() {
		return listaDeIndividuos.size();
	}

}