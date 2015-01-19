package br.com.vinyanalista.ag;

public interface DefinicaoDoIndividuo<Individuo> {
	
	public Individuo cruzar(Individuo outroIndividuo);
	
	public int fitness();
	
	public int fitnessDesejado();
	
	public void mutar();

}