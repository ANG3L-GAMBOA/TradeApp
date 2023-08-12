package clases;

public class Categoria {
	private String codigo;
	private String categoria;
	
	public Categoria() {
		super();
	}

	public Categoria(String codigo, String categoria) {
		super();
		this.codigo = codigo;
		this.categoria = categoria;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCategoria() {
		return this.categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
}
