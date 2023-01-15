package org.frank.hibernate.inheritance.models.single;


public class Folder extends WindowFile{
    
    
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
