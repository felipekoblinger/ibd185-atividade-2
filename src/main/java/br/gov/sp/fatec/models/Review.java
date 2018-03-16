package br.gov.sp.fatec.models;

import com.sun.istack.internal.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @SequenceGenerator(name = "reviews_id_seq", sequenceName = "reviews_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reviews_id_seq")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "person_id", nullable = false, insertable = false, updatable = false)
    private Long personId;

    @Column(name = "place_id", nullable = false, insertable = false, updatable = false)
    private Long placeId;

    @Column(name = "message", nullable = false, length = 255)
    private String message;

    @Column(name = "score", nullable = false)
    private int score;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "place_id")
    private Place place;

    /* Getters and Setters */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
