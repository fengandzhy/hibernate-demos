package org.frank.hibernate.inheritance.models.single;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("Folder")
public class Folder extends WindowFile{
    
    @Basic
    @Column(name = "FILE_COUNT")
    private Integer fileCount;

    public Integer getFileCount() {
        return fileCount;
    }

    public void setFileCount(Integer fileCount) {
        this.fileCount = fileCount;
    }

    @Override
    public String toString() {
        return "Folder{" +
                "fileCount=" + fileCount +
                '}';
    }
}
