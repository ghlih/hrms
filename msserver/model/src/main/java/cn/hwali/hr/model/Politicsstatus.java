package cn.hwali.hr.model;

import java.io.Serializable;
import java.util.Objects;

public class Politicsstatus implements Serializable {
    private static final long serialVersionUID = 3204711545301454850L;
    private Integer id;
    private String name;

    public Politicsstatus() {
    }

    public Politicsstatus(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Politicsstatus that = (Politicsstatus) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}