package mate.academy.internetshop.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import mate.academy.internetshop.idgenerators.ItemIdGenerator;

@Entity
@Table(name = "items")
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;
    @Column
    private String name;
    @Column(columnDefinition = "DECIMAL")
    private Double price;

    public Item(String name, Double price) {
        this.id = ItemIdGenerator.generateId();
        this.name = name;
        this.price = price;
    }

    public Item(Long id) {
        this.id = id;
    }

    public Item() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Item: " + name + ", " + price;
    }
}
