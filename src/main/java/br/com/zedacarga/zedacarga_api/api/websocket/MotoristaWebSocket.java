package br.com.zedacarga.zedacarga_api.api.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class MotoristaWebSocket {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  public void enviarMensagemParaMotorista(Long motoristaId, String mensagem) {
    // Envia a mensagem para o cliente no tópico /topic/motorista/{motoristaId}
    messagingTemplate.convertAndSend("/topic/motorista/" + motoristaId, mensagem);
  }
}
