package org.frank.hibernate.one2one.models;

public class Person {
    private long id;
    private String name;
    private Card card;

    @SuppressWarnings("unused")
    public Person(String name, Card card) {
        this.name = name;
        this.card = card;
    }

    public Person() {
    }

    public long getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @SuppressWarnings("JpaAttributeTypeInspection")
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", card=" + card +
                '}';
    }
}
