package techlab.proyecto.pedidos;

import jakarta.persistence.*;
import techlab.proyecto.productos.Producto;

@Entity
public class LineaPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Producto producto;

    private int cantidad;

    public LineaPedido() {}

    public LineaPedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public Long getId() { return id; }
    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }

    public double calcularSubtotal() {
        return producto.getPrecio() * cantidad;
    }
}