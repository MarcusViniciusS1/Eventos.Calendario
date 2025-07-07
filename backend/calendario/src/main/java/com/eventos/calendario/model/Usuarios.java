package com.eventos.calendario.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Usuarios extends EntityMaster{

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String username;

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

