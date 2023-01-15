package org.frank.hibernate.inheritance.models.joined;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "web_app_menu_id")
public class WebAppMenu extends BaseWebAppMenu{

    public String head;

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }
}
