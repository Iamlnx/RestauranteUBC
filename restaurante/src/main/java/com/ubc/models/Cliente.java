package com.ubc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ClienteID;
    private String Nome;
    private LocalDate DataDeCadastro;

    public Cliente() {}

    public Cliente(int ClienteID, String Nome, LocalDate DataDeCadastro) {
        this.ClienteID = ClienteID;
        this.Nome = Nome;
        this.DataDeCadastro = DataDeCadastro;
    }

    public int getClienteID() {
        return ClienteID;
    }

    public void setClienteID(int ClienteID) {
        this.ClienteID = ClienteID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public LocalDate getDataDeCadastro() {
        return DataDeCadastro;
    }

    public void setDataDeCadastro(LocalDate DataDeCadastro) {
        this.DataDeCadastro = DataDeCadastro;
    }
}