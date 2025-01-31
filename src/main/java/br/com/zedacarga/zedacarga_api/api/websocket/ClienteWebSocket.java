package br.com.zedacarga.zedacarga_api.api.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ClienteWebSocket {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public void enviarMensagemParaCliente(Long clienteId, String mensagem) {
    // Envia a mensagem para o cliente no tópico /topic/cliente/{clienteId}
    messagingTemplate.convertAndSend("/topic/cliente/" + clienteId, mensagem);
  }
}
