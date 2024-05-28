package com.ubc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "funcionario")
public class Funcionario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int FuncionarioID;
    private String Nome;
    private String Cargo;

    public Funcionario() {}

    public Funcionario(int FuncionarioID, String Nome, String Cargo) {
        this.FuncionarioID = FuncionarioID;
        this.Nome = Nome;
        this.Cargo = Cargo;
    }

    public int getFuncionarioID() {
        return FuncionarioID;
    }

    public void setFuncionarioID(int FuncionarioID) {
        this.FuncionarioID = FuncionarioID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }
}
