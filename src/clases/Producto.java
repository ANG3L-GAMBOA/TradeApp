package clases;

public class Producto {
    private String codigo;
    private String producto;
    private String costo;
    private String codigoMarca;
    private String codigoCategoria;

    public Producto() {
    }

    public Producto(String codigo, String producto, String costo, String codigoMarca, String codigoCategoria) {
        this.codigo = codigo;
        this.producto = producto;
        this.costo = costo;
        this.codigoMarca = codigoMarca;
        this.codigoCategoria = codigoCategoria;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public String getCodigoMarca() {
        return codigoMarca;
    }

    public void setCodigoMarca(String codigoMarca) {
        this.codigoMarca = codigoMarca;
    }

    public String getCodigoCategoria() {
        return codigoCategoria;
    }

    public void setCodigoCategoria(String codigoCategoria) {
        this.codigoCategoria = codigoCategoria;
    }
}