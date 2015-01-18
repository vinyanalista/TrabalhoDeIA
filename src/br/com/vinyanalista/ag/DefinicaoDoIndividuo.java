package br.com.vinyanalista.ag;

public interface DefinicaoDoIndividuo<Individuo> {
	
	public Individuo cruzar(DefinicaoDoIndividuo<Individuo> outroIndividuo);
	
	public int fitness();
	
	public void mutar();

}