package restmanager.view;

import java.util.Scanner;
import restmanager.controller.RestManagerController;
import restmanager.model.Order;
import restmanager.model.OrderItem;

/**
 * Clase que implementa a vista con menú de texto de aplicación
 *
 * @author Profe de Programación
 */
public class RestManagerView {

    // Referenza á controladora da app
    private RestManagerController controller;

    /**
     * Crea un novo obxecto de vista, asociado á controladora indicada
     *
     * @param controller Referenza á controladora da app
     */
    public RestManagerView(RestManagerController controller) {
        this.controller = controller;
    }

    /**
     * Pide un elemento da carta para engadilo a un pedido
     *
     * @param order Pedido no que queremos engadir o elemento
     * @return Id do elemento da carta introducido, ou cero se non se quere
     * engadir ningún elemento
     */
    public int askOrderItem(Order order) {
        Scanner scan = new Scanner(System.in);
        System.out.println("Introduza o número do elemento da carta (0 para rematar):");
        return scan.nextInt();
    }

    /**
     * Mostra unha mensaxe indicando que non hai ningún elemento na carta co
     * número introducido
     */
    public void showItemNotFoundMessage() {
        System.out.println("Non se atopou un elemento na carta con ese número!");
    }

    /**
     * Mostra os elementos e o prezo total dun pedido
     *
     * @param order Pedido que queremos mostrar
     */
    public void showOrderItems(Order order) {
        if (order != null) {
            System.out.println("Elementos do pedido: ");
            for (OrderItem item : order.getItems()) {
                System.out.println("- " + item.getQuantity() + " "
                        + item.getItem().getDescription());
            }
            System.out.println("Conta: " + order.getPrice() + "€");
        } else {
            System.out.println("Non hai ningún pedido para a mesa indicada!");
        }
    }

    /**
     * Mostra o menú inicial da aplicación
     *
     * @return true se o usuario quere pechar a aplicación, senón false
     */
    public boolean showInitMenu() {
        int option;
        Scanner scan = new Scanner(System.in);

        System.out.println("Benvido a RestManager");
        System.out.println("1. Engadir elementos a un pedido");
        System.out.println("2. Mostrar un pedido");
        System.out.println("3. Cobrar pedidos");
        System.out.println("4. Sair");
        System.out.println("Introduza unha opcion:");
        option = scan.nextInt();
        scan.nextLine();

        switch (option) {
            case 1:
                System.out.println("Introduza o numero de mesa:");
                controller.completeOrder(scan.nextInt());
                break;
            case 2:
                System.out.println("Introduza o numero de mesa:");
                controller.showOrder(scan.nextInt());
                break;
            case 3:
                controller.showOrders();
                break;

            case 4:
                return true;
        }
        return false;
    }
}
