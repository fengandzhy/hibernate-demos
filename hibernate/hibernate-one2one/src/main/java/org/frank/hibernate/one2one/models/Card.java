package org.frank.hibernate.one2one.models;

public class Card {
    private long Id;
    private String cardNo;
    private Person person;

    public Card(String cardNo) {
        this.cardNo = cardNo;
    }

    public Card() {
    }

    public long getId() {
        return Id;
    }

    @SuppressWarnings("unused")
    public void setId(long id) {
        Id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    @SuppressWarnings("unused")
    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    @SuppressWarnings("JpaAttributeTypeInspection")
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
