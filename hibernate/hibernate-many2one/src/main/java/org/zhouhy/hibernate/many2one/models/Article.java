package org.zhouhy.hibernate.many2one.models;

public class Article {
    private long id;
    private String name;
    private Author author;

    public Article() {
    }

    public Article(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Article(long id, String name, Author author) {
        this.id = id;
        this.name = name;
        this.author = author;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author=" + author +
                '}';
    }
}
