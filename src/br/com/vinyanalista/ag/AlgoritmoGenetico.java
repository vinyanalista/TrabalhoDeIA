package br.com.vinyanalista.ag;

public abstract class AlgoritmoGenetico<Individuo extends DefinicaoDoIndividuo<Individuo>> {

	protected abstract int fitnessDesejado();

	private boolean deveMutar() {
		return (Math.random() < probabilidadeDeMutacao());
	}

	public Individuo executar(Populacao<Individuo> populacaoInicial,
			int maximoDeIteracoes) {
		int iteracoes = 0;
		Individuo melhorIndividuo = null;
		Populacao<Individuo> populacaoAtual = populacaoInicial;
		Individuo solucao = null;
		do {
			Populacao<Individuo> novaPopulacao = new Populacao<Individuo>();
			for (int i = 0; i < populacaoAtual.tamanho(); i++) {
				Individuo individuo1 = populacaoAtual.individuoAleatorio();
				Individuo individuo2 = populacaoAtual.individuoAleatorio();
				Individuo filho = individuo1.cruzar(individuo2);
				if (deveMutar()) {
					filho.mutar();
				}
				novaPopulacao.adicionar(filho);
			}
			populacaoAtual = novaPopulacao;
			melhorIndividuo = populacaoAtual.melhorIndividuo();
			iteracoes++;
			if ((melhorIndividuo.fitness() >= fitnessDesejado())
					|| (iteracoes == maximoDeIteracoes)) {
				solucao = melhorIndividuo;
			}
		} while (solucao == null);
		return solucao;
	}

	protected abstract double probabilidadeDeMutacao();

}