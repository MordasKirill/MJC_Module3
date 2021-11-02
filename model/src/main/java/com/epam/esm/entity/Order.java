package com.epam.esm.entity;

import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Order entity can be serialized
 * Contains of:
 * int id
 * Integer certificateId
 * Double price
 * Date; date of order creation
 * Set<User> users set of users entities
 */
@Entity
@Table(name = "orders")
public class Order extends RepresentationModel<Order> implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull
    private int id;

    @Column(name = "cert_id")
    @Min(1)
    @NotNull
    private Integer certificateId;

    @Column
    private Double price;

    @Column(name = "date")
    private Date date;

    @ManyToMany(mappedBy = "orders")
    private Set<User> users;

    @PrePersist
    protected void onCreate() {
        setDate(new Date());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order that = (Order) o;
        return id == that.id && Objects.equals(certificateId, that.certificateId) && Objects.equals(price, that.price) && Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, certificateId, price, date);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", certificateId=" + certificateId +
                ", price=" + price +
                ", date=" + date +
                ", users=" + users +
                '}';
    }
}

