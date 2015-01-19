package br.com.vinyanalista.ag.aeroportos;

public class Cidade {
	
	private final int x;
	private final int y;

	public Cidade(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int distancia(Cidade outraCidade) {
		return Math.abs(x - outraCidade.x) + Math.abs(y - outraCidade.y);
	}
	
	@Override
	public boolean equals(Object outroObjeto) {
		if (!(outroObjeto instanceof Cidade)) {
			return false;
		}
		Cidade outraCidade = (Cidade) outroObjeto;
		return (x == outraCidade.x) && (y == outraCidade.y);
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	@Override
	public String toString() {
		return new StringBuilder("(").append(x).append(", ").append(y).append(")").toString();
	}
}