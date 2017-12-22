package br.exacta.json;


import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper);
        //Le o json pro objeto
        programacaoJSON programacao = objectMapper.readValue(new File("src/main/resources/programacao1.json"), programacaoJSON.class);
        System.out.println(programacaoJSON.class);
        System.out.println(programacao);
        
        //Grava o arquivo a partir do objeto
        objectMapper.writeValue(new File("src/main/resources/programacao_out1.json"), programacao);
        System.out.println("CRIADO JSON!");
    }

}
