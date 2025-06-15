package restmanager.model;

import java.util.ArrayList;

/**
 * Clase que implementa un pedido
 *
 * @author Profe de Programación
 */
public class Order {

    // Atributos privados. Omítese Javadoc
    private int tableNumber;
    private ArrayList<OrderItem> items;

    /**
     * Obtén o número de mesa do pedido
     *
     * @return Número de mesa do pedido
     */
    public int getTableNumber() {
        return tableNumber;
    }

    /**
     * Establece o número de mesa do pedido
     *
     * @param tableNumber Número de mesa para o pedido
     */
    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    /**
     * Obtén os elementos do pedido
     *
     * @return Lista cos elementos do pedido
     */
    public ArrayList<OrderItem> getItems() {
        return items;
    }

    /**
     * Establece os elementos do pedido
     *
     * @param items Lista cos elementos do pedido
     */
    public void setItems(ArrayList<OrderItem> items) {
        this.items = items;
    }

    /**
     * Obtén o prezo total do pedido, sumando o prezo de todos os seus elementos
     *
     * @return Prezo total do pedido
     */
    public double getPrice() {
        double price = 0;
        for (OrderItem item : items) {
            price += item.getPrice();
        }
        return price;
    }

    /**
     * Crea un novo pedido
     *
     * @param tableNumber Número de mesa para o pedido
     */
    public Order(int tableNumber) {
        this.tableNumber = tableNumber;
        items = new ArrayList<>();
    }
}
