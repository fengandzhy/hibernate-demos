package org.frank.hibernate.inheritance.models.joined;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "automation_id")
public class AutomationWebAppMenu extends BaseWebAppMenu{
    private String foot;

    public String getFoot() {
        return foot;
    }

    public void setFoot(String foot) {
        this.foot = foot;
    }
}
