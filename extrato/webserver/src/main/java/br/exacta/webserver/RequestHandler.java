/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.exacta.webserver;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

/**
 *
 * @author vitor
 */
public class RequestHandler {
    
    private static final int CODIGO_SUCESSO = 200;
    private static final int CODIGO_ERRO = 404;


    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange httpExchange) throws IOException {

            String metodo = httpExchange.getRequestMethod();
            String path = httpExchange.getRequestURI().getPath();
            String resposta = new String();
            int codigoRetorno = CODIGO_ERRO;

            String equipamento = path.split("/")[1];
            String solicitacao = path.split("/")[2];
            
            if (metodo.equals("GET") && solicitacao.equals("programacao.json") ) {
                
                // TODO:
                // Montar programacao.json do equimpamento e enviar como resposta
                resposta = "Enviar programacao.json de " + equipamento;
                codigoRetorno = CODIGO_SUCESSO;
            }
            
            if (metodo.equals("POST") && solicitacao.equals("resultados.json") ) {
                
                // TODO:
                // Receber o resultados.json, salvar no banco e retornar sucesso
                resposta = "Recebido resultado.json de " + equipamento;
                codigoRetorno = CODIGO_SUCESSO;
            }
            
            httpExchange.sendResponseHeaders(codigoRetorno, resposta.length());
            try (OutputStream os = httpExchange.getResponseBody()) {
                os.write(resposta.getBytes());
                os.close();
            }
        }
    }

}
