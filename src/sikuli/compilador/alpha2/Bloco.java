package sikuli.compilador.alpha2;

public class Bloco {
	
	private String codigo;
	private int delimitador;

	public Bloco(String codigo, int Delimitador) {
		this.codigo = codigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

}
