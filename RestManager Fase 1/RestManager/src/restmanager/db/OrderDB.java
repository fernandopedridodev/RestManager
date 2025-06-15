package restmanager.db;

import java.util.HashMap;
import restmanager.model.Order;
import restmanager.model.MenuItem;
import restmanager.model.OrderItem;

/**
 * Clase que implementa a persistencia dos pedidos e os elementos da carta
 *
 * @author Profe de Programación
 */
public class OrderDB {

    // Array cos elementos da carta
    private static final MenuItem[] menu = new MenuItem[]{
        new MenuItem(1, "Spaguetti Carbonara", 12.50),
        new MenuItem(2, "Pizza catro queixos mediana", 11.90),
        new MenuItem(3, "Cocacola 33 cl", 2.5),
        new MenuItem(4, "Auga 1l", 3)
    };

    // HashMap dos pedidos co Id como chave
    private static HashMap<Integer, Order> orders = new HashMap<>();

    /**
     * Obtén o elemento de menú cun Id determinado
     *
     * @param menuItemId Id do elemento de menú que buscamos
     * @return Elemento de menú con ese Id, ou null se non hai un elemento co Id
     * indicado
     */
    public static MenuItem findById(int menuItemId) {
        for (MenuItem item : menu) {
            if (item.getId() == menuItemId) {
                return item;
            }
        }
        return null;
    }

    /**
     * Obtén o pedido para un número de mesa determinado
     *
     * @param tableNumber Número de mesa para o que buscamos o pedido
     * @return Pedido asociado á mesa, ou null se non hai un pedido asociado á
     * mesa indicada
     */
    public static Order findByTable(int tableNumber) {
        return orders.get(tableNumber);
    }

    /**
     * Almacena un novo pedido
     *
     * @param order Pedido que se quere gardar
     */
    public static void save(Order order) {
        orders.put(order.getTableNumber(), order);
    }

    /**
     * Almacena un novo elemento nun pedido
     *
     * @param order Pedido do elemento
     * @param item Elemento do pedido que se quere gardar
     */
    public static void saveItem(Order order, OrderItem item) {
    }

    /**
     * Actualiza un elemento nun pedido
     *
     * @param order Pedido do elemento
     * @param item Elemento do pedido que se quere actualizar
     */
    public static void updateItem(Order order, OrderItem item) {
    }

}
