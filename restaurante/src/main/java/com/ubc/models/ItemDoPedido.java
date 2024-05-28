package com.ubc.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import java.math.BigDecimal;

@Entity
@Table(name = "itensdopedido")
public class ItemDoPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ItemDoPedidoID;

    @ManyToOne
    @JoinColumn(name = "PedidoID")
    private Pedidos pedido;

    @ManyToOne
    @JoinColumn(name = "PratoID")
    private Prato prato;

    private int Quantidade;
    private BigDecimal PrecoTotal;

    public ItemDoPedido() {}

    public ItemDoPedido(int ItemDoPedidoID, Pedidos pedido, Prato prato, int Quantidade, BigDecimal PrecoTotal) {
        this.ItemDoPedidoID = ItemDoPedidoID;
        this.pedido = pedido;
        this.prato = prato;
        this.Quantidade = Quantidade;
        this.PrecoTotal = PrecoTotal;
    }

    public int getItemDoPedidoID() {
        return ItemDoPedidoID;
    }

    public void setItemDoPedidoID(int ItemDoPedidoID) {
        this.ItemDoPedidoID = ItemDoPedidoID;
    }

    public Pedidos getPedido() {
        return pedido;
    }

    public void setPedido(Pedidos pedido) {
        this.pedido = pedido;
    }

    public Prato getPrato() {
        return prato;
    }

    public void setPrato(Prato prato) {
        this.prato = prato;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int Quantidade) {
        this.Quantidade = Quantidade;
    }

    public BigDecimal getPrecoTotal() {
        return PrecoTotal;
    }

    public void setPrecoTotal(BigDecimal PrecoTotal) {
        this.PrecoTotal = PrecoTotal;
    }
}