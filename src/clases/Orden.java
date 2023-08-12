package clases;

public class Orden {
    private String codigo;
    private String producto;
    private String usuario;
    private String cantidad;

    public Orden() {
    }

    public Orden(String codigo, String producto, String usuario, String cantidad) {
        this.codigo = codigo;
        this.producto = producto;
        this.usuario = usuario;
        this.cantidad = cantidad;
        this.cantidad = cantidad;
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

    public String getUsuario() {
        return this.usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getCantidad() {
        return this.cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }
}