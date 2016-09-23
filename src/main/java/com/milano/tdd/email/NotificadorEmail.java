package com.milano.tdd.email;

import com.milano.tdd.model.Pedido;
import com.milano.tdd.service.AcaoLancamentoPedido;

public class NotificadorEmail implements AcaoLancamentoPedido {

	@Override
	public void executar(Pedido pedido) {
		System.out.println("Enviando email para: " + pedido.getCliente().getEmail());
	}
}
