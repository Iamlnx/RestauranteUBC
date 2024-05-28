package com.ubc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.time.LocalDate;

@Entity
@Table(name = "pedidos")
public class Pedidos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PedidoID;

    @ManyToOne
    @JoinColumn(name = "ClienteID")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "FuncionarioID")
    private Funcionario funcionario;

    private LocalDate DataDoPedido;
    private String Status;

    public Pedidos() {}

    public Pedidos(int PedidoID, Cliente cliente, Funcionario funcionario, LocalDate DataDoPedido, String Status) {
        this.PedidoID = PedidoID;
        this.cliente = cliente;
        this.funcionario = funcionario;
        this.DataDoPedido = DataDoPedido;
        this.Status = Status;
    }

    public int getPedidoID() {
        return PedidoID;
    }

    public void setPedidoID(int PedidoID) {
        this.PedidoID = PedidoID;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDate getDataDoPedido() {
        return DataDoPedido;
    }

    public void setDataDoPedido(LocalDate DataDoPedido) {
        this.DataDoPedido = DataDoPedido;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String Status) {
        this.Status = Status;
    }
}