package restmanager.model;

/**
 * Clase que implementa un elemento dun pedido
 *
 * @author Profe de Programación
 */
public class OrderItem {

    // Atributos privados. Omítese Javadoc
    private MenuItem item;
    private int quantity;

    /**
     * Obtén o elemento da carta asociado con este elemento do pedido
     *
     * @return Elemento da carta
     */
    public MenuItem getItem() {
        return item;
    }

    /**
     * Establece o elemento da carta asociado con este elemento do pedido
     *
     * @param item Elemento da carta
     */
    public void setItem(MenuItem item) {
        this.item = item;
    }

    /**
     * Obtén a cantidade de elementos da carta incluídos neste elemento do
     * pedido
     *
     * @return Cantidade de elementos
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Establece a cantidade de elementos da carta incluídos neste elemento do
     * pedido
     *
     * @param quantity Cantidade de elementos
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    /**
     * Devolve o prezo deste elemento do pedido
     * @return Prezo do elemento do pedido, multiplicando o prezo do elemento da
     * carta pola cantidade.
     */
    public double getPrice() {
        return item.getPrice() * quantity;
    }

    /**
     * Crea un novo elemento do pedido
     *
     * @param item Elemento do menú asociado con este elemento do pedido
     * @param quantity Cantidade de elementos
     */
    public OrderItem(MenuItem item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}
