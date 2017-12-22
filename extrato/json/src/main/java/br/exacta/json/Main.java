package br.exacta.json;


import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

    public static void main(String[] args) throws Exception {
        
        // TESTE PARA O PROGRAMA�AO
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper);
        //Le o json pro objeto
        programacaoJSON programacao = objectMapper.readValue(new File("src/main/resources/programacao1.json"), programacaoJSON.class);
        System.out.println(programacaoJSON.class);
        System.out.println(programacao);
        
        //Grava o arquivo a partir do objeto
        objectMapper.writeValue(new File("src/main/resources/programacao_out1.json"), programacao);
        System.out.println("CRIADO JSON!");
        
        
        // TESTE PARA O RESULTADO
        ObjectMapper objMapper = new ObjectMapper();
        
        // Leitura do json e manda ao obj
        resultadoJSON resultado = objMapper.readValue("", resultadoJSON.class);
        
        
        
        
    }

}
