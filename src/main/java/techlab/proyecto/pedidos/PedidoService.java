package techlab.proyecto.pedidos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import techlab.proyecto.excepciones.StockInsuficienteException;
import techlab.proyecto.productos.Producto;
import techlab.proyecto.productos.ProductoService;

import java.util.List;
import java.util.Optional;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ProductoService productoService;

    public Pedido crearPedido(List<LineaPedido> lineas) {
        Pedido pedido = new Pedido();

        for (LineaPedido linea : lineas) {
            Optional<Producto> optProducto = productoService.buscarPorId(linea.getProducto().getId());

            if (optProducto.isEmpty()) {
                throw new RuntimeException("Producto no encontrado con ID: " + linea.getProducto().getId());
            }

            Producto producto = optProducto.get();

            if (producto.getStock() < linea.getCantidad()) {
                throw new StockInsuficienteException("Stock insuficiente para el producto: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - linea.getCantidad());
            productoService.actualizarProducto(producto.getId(), producto.getPrecio(), producto.getStock());

            LineaPedido nuevaLinea = new LineaPedido(producto, linea.getCantidad());
            pedido.agregarLinea(nuevaLinea);
        }

        return pedidoRepository.save(pedido);
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }
}
