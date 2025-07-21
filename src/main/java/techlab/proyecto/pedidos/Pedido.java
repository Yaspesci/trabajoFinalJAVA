package techlab.proyecto.pedidos;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "pedido_id")
    private List<LineaPedido> lineas = new ArrayList<>();

    public Pedido() {}

    public Long getId() { return id; }
    public List<LineaPedido> getLineas() { return lineas; }

    public void agregarLinea(LineaPedido linea) {
        lineas.add(linea);
    }

    public double calcularTotal() {
        return lineas.stream()
                .mapToDouble(LineaPedido::calcularSubtotal)
                .sum();
    }
}
