package com.demiashkevich.movie.entity;

import java.util.List;

public class Role extends Entity {

    private int roleId;
    private String name;
    private List<Crew> crews;

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Crew> getCrews() {
        return crews;
    }

    public void setCrews(List<Crew> crews) {
        this.crews = crews;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        if (roleId != role.roleId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return roleId;
    }
}
