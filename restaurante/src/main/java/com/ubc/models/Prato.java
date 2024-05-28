package com.ubc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "prato")
public class Prato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PratoID;
    private String Nome;
    private String Descricao;
    private BigDecimal Preco;

    public Prato() {}

    public Prato(int PratoID, String Nome, String Descricao, BigDecimal Preco) {
        this.PratoID = PratoID;
        this.Nome = Nome;
        this.Descricao = Descricao;
        this.Preco = Preco;
    }

    public int getPratoID() {
        return PratoID;
    }

    public void setPratoID(int PratoID) {
        this.PratoID = PratoID;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public BigDecimal getPreco() {
        return Preco;
    }

    public void setPreco(BigDecimal Preco) {
        this.Preco = Preco;
    }
}