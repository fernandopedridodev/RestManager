package restmanager.db;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import restmanager.model.Order;
import restmanager.model.MenuItem;
import restmanager.model.OrderItem;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

/**
 * Clase que implementa a persistencia dos pedidos e os elementos da carta
 *
 * @author Profe de Programación
 */
public class OrderDB {

    // Array cos elementos da carta
    private static ArrayList<MenuItem> menu = null;

    // Conexión coa base de datos
    private static Connection c = null;

    /**
     * Método que establece a conexión coa base de datos se é necesario
     *
     * @throws SQLException
     */
    private static void getConnection() throws SQLException {
        if (c == null) {
            c = DriverManager.getConnection("jdbc:sqlite:rest.db");
        }
    }

    /**
     * Método que pecha a conexión coa base de datos
     */
    public static void closeConnection() {
        if (c != null) {
            System.out.print("Pechando conexión coa base de datos...");
            try {
                c.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
            System.out.println("[OK]");
        }
    }

    /**
     * Metodo que carga o menu dun ficheiro de texto
     */
    private static void loadMenu() {
        // Declaramos o scanner
        Scanner in = null;
        // Inicializamos o ArrayList do menu
        menu = new ArrayList<>();
        try {
            // Abrimos o scanner sobre un reader con buffer
            in = new Scanner(new BufferedReader(new FileReader("menu.txt")));
            // Establecemos como delimitador ;
            in.useDelimiter(";");
            // Lemos os datos de tres en tres e engadimos os elementos do menu
            while (in.hasNext()) {
                menu.add(new MenuItem(in.nextInt(), in.next(), in.nextDouble()));
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        } finally {
            // En calquera caso, producirase ou non unha excepción, pechamos o
            // scanner se está aberto
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * Obtén o elemento de menú cun Id determinado
     *
     * @param menuItemId Id do elemento de menú que buscamos
     * @return Elemento de menú con ese Id, ou null se non hai un elemento co Id
     * indicado
     */
    public static MenuItem findById(int menuItemId) {
        // Se o menu e nulo cargamos o menu do ficheiro
        if (menu == null) {
            loadMenu();
        }
        // Buscamos o elemento do menu no ArrayList
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
        Order order = null;
        try {
            // Establecemos a conexión coa base de datos se é necesario
            getConnection();
            // Executamos a consulta que obtén o pedido
            String sql = "SELECT * FROM orders WHERE table_number=?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, tableNumber);
            ResultSet rst = pst.executeQuery();
            // Xeramos o pedido resultante cos seus elementos
            if (rst.next()) {
                order = new Order(tableNumber);
                // Executamos a consulta que obtén os elementos do pedido
                String sqlItems = "SELECT * FROM order_items WHERE table_number=?";
                PreparedStatement pstItems = c.prepareStatement(sqlItems);
                pstItems.setInt(1, tableNumber);
                ResultSet rstItems = pstItems.executeQuery();
                while (rstItems.next()) {
                    OrderItem item = new OrderItem(findById(rstItems.getInt("item_id")),
                            rstItems.getInt("quantity"));
                    order.getItems().add(item);
                }
                // Pechamos os recursos
                rstItems.close();
                pstItems.close();
            }
            // Pechamos os recursos
            rst.close();
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return order;
    }

    /**
     * Almacena un novo pedido
     *
     * @param order Pedido que se quere gardar
     */
    public static void save(Order order) {
        try {
            // Establecemos a conexión coa base de datos se é necesario
            getConnection();
            // Executamos a consulta de inserción do pedido
            String sql = "INSERT INTO orders VALUES(?)";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, order.getTableNumber());
            pst.executeUpdate();
            // Pechamos os recursos
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Almacena un novo elemento nun pedido
     *
     * @param order Pedido do elemento
     * @param item Elemento do pedido que se quere gardar
     */
    public static void saveItem(Order order, OrderItem item) {
        try {
            // Establecemos a conexión coa base de datos se é necesario
            getConnection();
            // Executamos a consulta de inserción do pedido
            String sql = "INSERT INTO order_items VALUES(?,?,?)";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, order.getTableNumber());
            pst.setInt(2, item.getItem().getId());
            pst.setInt(3, item.getQuantity());
            pst.executeUpdate();
            // Pechamos os recursos
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Actualiza un elemento nun pedido
     *
     * @param order Pedido do elemento
     * @param item Elemento do pedido que se quere actualizar
     */
    public static void updateItem(Order order, OrderItem item) {
        try {
            // Establecemos a conexión coa base de datos se é necesario
            getConnection();
            // Executamos a consulta de inserción do pedido
            String sql = "UPDATE order_items SET quantity=? WHERE "
                    + "table_number=? AND item_id=?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, item.getQuantity());
            pst.setInt(2, order.getTableNumber());
            pst.setInt(3, item.getItem().getId());
            pst.executeUpdate();
            // Pechamos os recursos
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Obtén a lista con todos os pedidos
     *
     * @return Lista con todos os pedidos
     */
    public static ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();
        try {
            // Establecemos a conexión coa base de datos se é necesario
            getConnection();
            // Executamos a consulta que obtén os elementos do pedido
            String sql = "SELECT * FROM orders";
            Statement st = c.createStatement();
            ResultSet rst = st.executeQuery(sql);
            // Xeramos o pedido resultante cos seus elementos
            while (rst.next()) {
                orders.add(new Order(rst.getInt("table_number")));
            }
            // Pechamos os recursos
            rst.close();
            st.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return orders;
    }

    /**
     * Elimina un pedido
     *
     * @param tableNumber Mesa á que está asociada o pedido a borrar
     */
    public static void removeOrder(int tableNumber) {
        try {
            // Establecemos a conexión coa base de datos se é necesario
            getConnection();
            // Executamos a consulta de borrado dos elementos do pedido
            String sql = "DELETE FROM order_items WHERE table_number=?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, tableNumber);
            pst.executeUpdate();
            // Executamos a consulta de borrado do pedido
            sql = "DELETE FROM orders WHERE table_number=?";
            pst = c.prepareStatement(sql);
            pst.setInt(1, tableNumber);
            pst.executeUpdate();
            // Pechamos os recursos
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Elimina un elemento dun pedido
     *
     * @param order Pedido ao que se lle quere eliminar o elemento
     * @param item Elemento a eliminar
     */
    public static void removeOrderItem(Order order, OrderItem item) {
        order.getItems().remove(item);
        try {
            // Establecemos a conexión coa base de datos se é necesario
            getConnection();
            // Executamos a consulta de borrado do elementos do pedido
            String sql = "DELETE FROM order_items WHERE table_number=? AND "
                    + "item_id=?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, order.getTableNumber());
            pst.setInt(2, item.getItem().getId());
            pst.executeUpdate();
            // Pechamos os recursos
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Decrementa unha unidade na cantidade dun elemento dun pedido
     *
     * @param order Pedido no que está o elemento
     * @param item Elmento ao que se lle quere quitar unha unidade na cantidade
     */
    public static void decreaseOrderItem(Order order, OrderItem item) {
        item.setQuantity(item.getQuantity() - 1);
        try {
            // Establecemos a conexión coa base de datos se é necesario
            getConnection();
            // Executamos a consulta de actualización do elemento do pedido
            String sql = "UPDATE order_items SET quantity=? WHERE "
                    + "table_number=? AND item_id=?";
            PreparedStatement pst = c.prepareStatement(sql);
            pst.setInt(1, item.getQuantity());
            pst.setInt(2, order.getTableNumber());
            pst.setInt(3, item.getItem().getId());
            pst.executeUpdate();
            // Pechamos os recursos
            pst.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
