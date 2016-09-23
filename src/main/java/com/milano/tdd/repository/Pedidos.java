package com.milano.tdd.repository;

import com.milano.tdd.model.Pedido;
import com.milano.tdd.service.AcaoLancamentoPedido;

public class Pedidos implements AcaoLancamentoPedido {

	@Override
	public void executar(Pedido pedido) {
		System.out.println("Salvando no banco de dados...");
	}
	
	public Pedido buscarPeloCodigo(Long codigo) {
		//Ele iria fazer a busca no banco de dados
		return new Pedido();
	}
}
