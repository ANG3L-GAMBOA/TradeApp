package clases;

public class Marca {
	private String codigo;
	private String marca;
	
	public Marca() {
		super();
	}

	public Marca(String codigo, String marca) {
		super();
		this.codigo = codigo;
		this.marca = marca;
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMarca() {
		return this.marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}
}
