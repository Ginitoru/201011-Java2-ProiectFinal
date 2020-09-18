package server.model.autovehicle;

import server.model.client.Client;
import server.model.user.User;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

@Entity
@Table(name = "service_order")
@NamedQuery(name ="ServiceOrcer.findAll", query = "SELECT s FROM ServiceOrder s")
public class ServiceOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;

    private double total;

    private Instant timeStamp;


    public ServiceOrder(double total, Instant timeStamp) {
        this.total = total;
        this.timeStamp = timeStamp;
    }


    public ServiceOrder() {
    }

    @ManyToOne
    private Client client;

    public Client getClient() {
        return client;
    }

    @ManyToOne
    private User user;

    @ElementCollection
    @CollectionTable(name = "problems_of_the_car")
    private List<String> carProblems = new ArrayList<>();

    @ManyToMany()
    @JoinTable(name = "parts_order")
    private Collection<Part> parts = new HashSet<>();

    @ManyToOne
    private Vehicle vehicle;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public List<String> getCarProblems() {
        return carProblems;
    }

    public void setCarProblems(List<String> carProblems) {
        this.carProblems = carProblems;
    }

    public Collection<Part> getParts() {
        return parts;
    }

    public void setParts(Collection<Part> parts) {
        this.parts = parts;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceOrder that = (ServiceOrder) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}