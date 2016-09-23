package com.milano.tdd.service;

import java.util.List;

import com.milano.tdd.model.Pedido;
import com.milano.tdd.model.StatusPedido;
import com.milano.tdd.repository.Pedidos;

public class PedidoService {
	
	private List<AcaoLancamentoPedido> acoes;
	private Pedidos pedidos;

	public PedidoService(Pedidos pedidos, List<AcaoLancamentoPedido> acoes) {
		this.pedidos = pedidos;
		this.acoes = acoes;
	}

	public double lancar(Pedido pedido) {
		double imposto = pedido.getValor() * 0.1;
		
		//Percorro minha lista de ações e executo uma ação.
		for (AcaoLancamentoPedido acao : acoes) {
			acao.executar(pedido);
		}

		return imposto;
	}

	public Pedido pagar(Long codigo) {
		Pedido pedido = pedidos.buscarPeloCodigo(codigo);
		
		if(!pedido.getStatusPedido().equals(StatusPedido.PENDENTE))
			throw new StatusPedidoInvalidoException();
		
		pedido.setStatusPedido(StatusPedido.PAGO);
		return pedido;
	}

}
