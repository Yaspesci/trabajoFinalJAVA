
package techlab.proyecto.productos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Producto agregarProducto(String nombre, double precio, int stock) {
        Producto nuevo = new Producto(nombre, precio, stock);
        return productoRepository.save(nuevo);
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Optional<Producto> buscarPorId(Long id) {
        return productoRepository.findById(id);
    }

    public Optional<Producto> buscarPorNombre(String nombre) {
        return productoRepository.findByNombreIgnoreCase(nombre);
    }

    public Optional<Producto> actualizarProducto(Long id, Double nuevoPrecio, Integer nuevoStock) {
        return productoRepository.findById(id).map(producto -> {
            if (nuevoPrecio != null && nuevoPrecio >= 0) {
                producto.setPrecio(nuevoPrecio);
            }
            if (nuevoStock != null && nuevoStock >= 0) {
                producto.setStock(nuevoStock);
            }
            return productoRepository.save(producto);
        });
    }

    public boolean eliminarProducto(Long id) {
        if (productoRepository.existsById(id)) {
            productoRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
