package com.milano.tdd.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

import com.milano.tdd.email.NotificadorEmail;
import com.milano.tdd.model.Pedido;
import com.milano.tdd.model.StatusPedido;
import com.milano.tdd.model.builder.PedidoBuilder;
import com.milano.tdd.repository.Pedidos;
import com.milano.tdd.sms.NotificadorSms;

public class PedidoServiceTest {

	private Pedido pedido;
	private PedidoService pedidoService;

	@Mock
	private Pedidos pedidos;

	@Mock
	private NotificadorEmail email;

	@Mock
	private NotificadorSms sms;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		pedido = new PedidoBuilder().comValor(100.0).para("Maria dos Santos Nunes", "maria@gmail.com", "(21)3778-4982")
				.construir();
		List<AcaoLancamentoPedido> acoes = Arrays.asList(email, sms, pedidos);
		pedidoService = new PedidoService(pedidos, acoes);
	} 

	@Test
	public void deveCalcularOImposto() throws Exception {
		double imposto = pedidoService.lancar(pedido);
		assertEquals(10.0, imposto, 0.0001);
	}

	@Test
	public void deveSalvarPedidoNoBancoDeDados() throws Exception {
		pedidoService.lancar(pedido);
		// Verifico no meu objeto falso(Mock) se pedido foi guardado com
		// sucesso!
		verify(pedidos).executar(pedido);
	}

	@Test
	public void deveNotificarPorEmail() throws Exception {
		pedidoService.lancar(pedido);
		verify(email).executar(pedido);
	}
	
	@Test
	public void deveNotificarPorSms() throws Exception {
		pedidoService.lancar(pedido);
		verify(sms).executar(pedido);
	}
	
	@Test
	public void devePagarPedidoPendente() throws Exception {
		
		Long codigoPedido = 135L;
		Pedido pedidoPendente = new PedidoBuilder()
						.comValor(200.0)
						.para("Emanuel da Silva", "manel@gmail.com", "2194556455")
						.construir();
		when(pedidos.buscarPeloCodigo(codigoPedido))
			   .thenReturn(pedidoPendente);
		
		Pedido pedidoPago = pedidoService.pagar(codigoPedido);
		assertEquals(StatusPedido.PAGO, pedidoPago.getStatusPedido());
	}
	
	@Test(expected = StatusPedidoInvalidoException.class)
	public void deveNegarPagamento() throws Exception {
		Long codigoPedido = 135L;
		
		Pedido pedidoPendente = new Pedido();
		pedidoPendente.setStatusPedido(StatusPedido.PAGO);
		
		when(pedidos.buscarPeloCodigo(codigoPedido)).thenReturn(pedidoPendente);
		
		pedidoService.pagar(codigoPedido);
		
	}
}
