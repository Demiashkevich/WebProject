package com.demiashkevich.movie.entity;

public class Crew extends Entity {

    private int crewId;
    private String firstName;
    private String lastName;
    private Role role;

    public int getCrewId() {
        return crewId;
    }

    public void setCrewId(int crewId) {
        this.crewId = crewId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Crew)) return false;

        Crew crew = (Crew) o;

        return crewId == crew.crewId && !(role != null ? !role.equals(crew.role) : crew.role != null);

    }

    @Override
    public int hashCode() {
        int result = crewId;
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
