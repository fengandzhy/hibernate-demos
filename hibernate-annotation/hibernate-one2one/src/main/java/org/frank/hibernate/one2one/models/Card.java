package org.frank.hibernate.one2one.models;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;

@Entity
@Table(name = "t1_card")
public class Card {
    
    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long Id;
    
    @Column(name = "card_no",nullable = false)
    private String cardNo;

    @OneToOne(mappedBy = "card",cascade = CascadeType.ALL)
    @Fetch(FetchMode.SELECT)
    private Person person;

    public Card(String cardNo) {
        this.cardNo = cardNo;
    }

    public Card() {
    }

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "Card{" +
                "Id=" + Id +
                ", cardNo='" + cardNo + '\'' +
                '}';
    }
}
