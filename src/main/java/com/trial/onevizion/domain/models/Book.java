package com.trial.onevizion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_generator")
    @SequenceGenerator(name = "book_generator", sequenceName = "book_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String author;
    private String description;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Book)) {
            return false;
        }
        Book other = (Book) Hibernate.unproxy(obj);
        return Objects.equals(id, other.getId());
    }

    @Override
    public final int hashCode() {
        if (this instanceof HibernateProxy) {
            return Hibernate.unproxy(this).hashCode();
        } else {
            return getClass().hashCode();
        }
    }
}
