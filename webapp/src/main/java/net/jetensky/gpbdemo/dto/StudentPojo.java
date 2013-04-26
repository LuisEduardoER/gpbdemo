package net.jetensky.gpbdemo.dto;

/**
 * This is POJO used by old style JSON web service
 * (see net.jetensky.gpbdemo.service.MyResource#getStudent())
 */
public class StudentPojo {
    private Long id;
    private String firstName;
    private String surName;
    private String faculty;

    public StudentPojo(Long id, String firstName, String lastName, String faculty) {
        this.id = id;
        this.firstName = firstName;
        this.surName = lastName;
        this.faculty = faculty;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
