package com.cobi.puresurveyandroid.model;

public class RoleOmp implements Comparable<RoleOmp>  {
     private String role;
     private RolesEnum rolesEnum;



    @Override
    public int compareTo(RoleOmp o) {
        return this.rolesEnum.compareTo(o.rolesEnum);
    }

    public RoleOmp(String role, RolesEnum rolesEnum) {
        this.role = role;
        this.rolesEnum = rolesEnum;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public RolesEnum getRolesEnum() {
        return rolesEnum;
    }

    public void setRolesEnum(RolesEnum rolesEnum) {
        this.rolesEnum = rolesEnum;
    }
}
