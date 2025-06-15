package restmanager.model;

/**
 * Clase que implementa un elemento da carta
 *
 * @author Profe de Programación
 */
public class MenuItem {

    // Atributos privados. Omítese Javadoc
    private int id;
    private String description;
    private double price;

    /**
     * Obtén o Id do elemento da carta
     *
     * @return Id do elemento
     */
    public int getId() {
        return id;
    }

    /**
     * Establece o Id do elemento carta
     *
     * @param id Id do elemento da carta
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtén a descrición do elemento da carta
     *
     * @return Descrición do elemento
     */
    public String getDescription() {
        return description;
    }

    /**
     * Establece a descrición do elemento da carta
     *
     * @param description Descrición para o elemento
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Obtén o prezo do elemento da carta
     *
     * @return Prezo do elemento
     */
    public double getPrice() {
        return price;
    }

    /**
     * Establece o prezo do elemento da carta
     *
     * @param price Prezo para o elemento da carta
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Crea un elemento da carta do restaurante
     *
     * @param id Id do elemento
     * @param description Descrición do elemento
     * @param price Prezo do elemento
     */
    public MenuItem(int id, String description, double price) {
        this.id = id;
        this.description = description;
        this.price = price;
    }
}
