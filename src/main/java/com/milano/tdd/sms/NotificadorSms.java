package com.milano.tdd.sms;

import com.milano.tdd.model.Pedido;
import com.milano.tdd.service.AcaoLancamentoPedido;

public class NotificadorSms implements AcaoLancamentoPedido {

	@Override
	public void executar(Pedido pedido) {
		System.out.println("Enviando SMS para o numero: " + pedido.getCliente().getTelefone());
	}
}
