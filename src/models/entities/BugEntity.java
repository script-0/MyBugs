/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models.entities;

import java.util.HashSet;
import java.util.Set;

import java.io.Serializable;
import java.util.Date;
/**
 *
 * @author Isaac
 */
public class BugEntity implements Serializable{
    private long id;
    
    private String label;
    
    private Date creationDate;
    
    private Date lastUpdateDate;
    
    private String solution;
    
    private boolean resolved;
    
    private Set<BugFile> files= new HashSet<>();

    public BugEntity(long id, String label, String solution, boolean resolved) {
        this.id = id;
        this.label = label;
        this.solution = solution;
        this.resolved = resolved;
    }

    public BugEntity(long id, String label, String solution, Date creationDate, Date lastUpdateDate, boolean resolved) {
        this.id = id;
        this.label = label;
        this.creationDate = creationDate;
        this.lastUpdateDate = lastUpdateDate;
        this.solution = solution;
        this.resolved = resolved;
    }

    public BugEntity() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastUpdateDate() {
        return lastUpdateDate;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public boolean isResolved() {
        return resolved;
    }

    public void setResolved(boolean resolved) {
        this.resolved = resolved;
    }

    public Set<BugFile> getFiles() {
        return files;
    }

    public void setFiles(Set<BugFile> files) {
        this.files = files;
    }    
}
