package restmanager.controller;

import restmanager.db.OrderDB;
import restmanager.model.MenuItem;
import restmanager.model.Order;
import restmanager.model.OrderItem;
import restmanager.view.OrderManagerView;
import restmanager.view.OrderView;
import restmanager.view.RestManagerView;

/**
 * Clase que implementa o controlador que xestiona o fluxo de comportamento da
 * aplicación
 *
 * @author Profe de Programación
 */
public class RestManagerController {

    // Referenza á vista da app
    private RestManagerView view = new RestManagerView(this);
    // Referenza á vista de xestión dos pedidos
    private OrderManagerView ordersView;

    /**
     * Método que inicia a aplicación, mostrando o menú de inicio ata que se
     * queira saír
     */
    public void initApp() {
        boolean exit;

        do {
            exit = view.showInitMenu();
        } while (!exit);
    }

    /**
     * Crea un novo pedido e o completa pedindo todos os pratos que se queiran
     * engadir, ou engade pratos a un pedido existente
     *
     * @param tableNumber Número da mesa para a que queremos completar o pedido
     */
    public void completeOrder(int tableNumber) {
        // Buscamos se hai un pedido para a mesa recibida
        Order order = OrderDB.findByTable(tableNumber);
        // Se non hai un pedido para a mesa, creámolo e gardámolo
        if (order == null) {
            order = new Order(tableNumber);
            OrderDB.save(order);
        }
        // Pedimos elementos da carta para engadir ao pedido, ata que se indique
        // que non se queren engadir máis elementos
        int menuItemId;
        do {
            // Chamamos á vista para pedir un elemento
            menuItemId = view.askOrderItem(order);
            // Engaimos o elemento se o Id introducido non é 0
            if (menuItemId != 0) {
                // Buscamos un elemento co Id introducido
                MenuItem item = OrderDB.findById(menuItemId);
                // Se non hai un elemento co Id introducido, informamos do erro
                if (item == null) {
                    view.showItemNotFoundMessage();
                } else {
                    // Buscamos nos elementos do pedido se xa hai un elemento
                    // asociado ao elemento da carta pedido, para nese caso
                    // incrementar a cantidade dese elemento
                    boolean menuItemFound = false;
                    for (OrderItem orderItem : order.getItems()) {
                        if (orderItem.getItem().getId() == item.getId()) {
                            orderItem.setQuantity(orderItem.getQuantity() + 1);
                            // Actualizamos os datos do elemento
                            OrderDB.updateItem(order, orderItem);
                            menuItemFound = true;
                        }
                    }
                    // Se non hai elemento no pedido para o elemento da carta
                    // pedido, engadimos un novo elemento no pedido con
                    // cantidade 1
                    if (!menuItemFound) {
                        OrderItem orderItem = new OrderItem(item, 1);
                        order.getItems().add(orderItem);
                        // Gardamos os datos do elemento
                        OrderDB.saveItem(order, orderItem);
                    }
                }
            }
        } while (menuItemId != 0);
    }

    /**
     * Obtén os datos dun pedido para mostralo por pantalla
     *
     * @param tableNumber Número de mesa para o que queremos ver o pedido
     */
    public void showOrder(int tableNumber) {
        Order order = OrderDB.findByTable(tableNumber);
        view.showOrderItems(order);
    }

    /**
     * Mostra todos os pedidos
     */
    public void showOrders() {
        ordersView = new OrderManagerView(this);
        ordersView.setOrders(OrderDB.getAllOrders());
        ordersView.setVisible(true);
    }

    /**
     * Actualiza a lista de todos os pedidos
     */
    public void refreshOrders() {
        ordersView.setOrders(OrderDB.getAllOrders());
    }

    /**
     * Mostra un diálogo cos datos dun pedido
     *
     * @param order Pedido que se quere mostrar
     */
    public void showOrderDetails(Order order) {
        // Creamos o cadro de diálogo e mostrámolo
        OrderView orderView = new OrderView(ordersView, true, this);
        orderView.setOrder(order);
        orderView.setLocationRelativeTo(ordersView);
        orderView.setVisible(true);
        // Se se indicou a opción de cobrar o pedido eliminámolo
        if (orderView.isOrderPaid()) {
            OrderDB.removeOrder(order.getTableNumber());
        }
        // Actualizamos a lista de todos pedidos
        ordersView.setOrders(OrderDB.getAllOrders());
    }

    /**
     * Borra unha unidade dun elemento do pedido
     *
     * @param order Pedido do que se quere quitar a unidade
     * @param index Índice do elemento do que se quere quitar a unidade
     */
    public void removeOrderItem(Order order, int index) {
        OrderItem item = order.getItems().get(index);
        if (item.getQuantity() == 1) {
            OrderDB.removeOrderItem(order, item);
        } else {
            OrderDB.decreaseOrderItem(order, item);
        }
    }

    /**
     * Método inicial
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        RestManagerController controller = new RestManagerController();
        controller.initApp();
    }

}
