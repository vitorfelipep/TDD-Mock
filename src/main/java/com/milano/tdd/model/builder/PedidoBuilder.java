package com.milano.tdd.model.builder;

import com.milano.tdd.model.Cliente;
import com.milano.tdd.model.Pedido;
import com.milano.tdd.model.StatusPedido;

public class PedidoBuilder {
	
	private Pedido instancia;

	public PedidoBuilder() {
		instancia = new Pedido();
	}
	
	public Pedido construir() {
		return instancia;
	}
	
	public PedidoBuilder comValor(double valor) {
		instancia.setValor(valor);
		return this;
	}
	
	public PedidoBuilder para(String nome, String email, String telefone) {
		Cliente cliente = new Cliente(nome, email, telefone);
		instancia.setCliente(cliente);
		situacaoPedido();
		return this;
	}
	
	public void situacaoPedido() {
		instancia.setStatusPedido(StatusPedido.PENDENTE);
	}
	
}
