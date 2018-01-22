# Projeto exTrato - Sistema de Trato Bovino

Consiste em um sistema que auxilia no processo de trabalho feito em campo na Balança de Trato Bovino, isto é, a **`EX3001L`**.
O sistema conta com as seguintes características:

- Cadastro básicos do sistema para perfeito uso: **Usuário**, **Equipamentos**, **Currais**, **Ingredientes**, e **Receitas**;
- Funções fundamentais, que justificam o uso do sistema: **Ordem de Produção**, e **Programação de Produção**;
- Funções de Saída do sistema: **Relatórios**, **Tabela de Resultados**;
- Diferenciais e facilidades que o sistema dispõem: Exportação e importação de arquivos de extensão **'.csv'**, **'xls, xlsx'**.


## Conhecendo melhor o sistema

Estas instruções irão levá-lo a uma cópia do projeto em funcionamento em sua máquina local para fins de desenvolvimento e teste. Consulte a implantação de notas sobre como implantar o projeto em sua máquina.

### Pré-requisitos de ambiente e software

Para instalação do sistema **`exTrato`**, são necessários pré-requisitos, o que levará ao melhor uso desta ferramenta.

```
```

## Instalação do sistema exTrato

Abaixo teremos uma sequência de passos para que seja instalado com êxito o sistema exTrato, e o não cumprimento destes passos implicará a não instalação do sistema. Segue abaixo:

* **PASSO 1:**
Após o clique no executável `exTratoInstall.exe`, a seguinte tela irá aparecer:

* **PASSO 2:**
Neste passo será exibido os termos de uso e privacidade, explicando todo o funcionamento e requerimentos que o sistema fará com uso de informações da empresa, tal como do usuário. Você poderá aceitar tudo que está descrito ou não, no entanto isso implicará no prosseguimento até o final da instalação, terminando antes de finalizar o instalador e com isso o sistema não será instalado.

* **PASSO 3:**
Seguindo o passo anterior será apresentado a tela abaixo, onde teremos o caminho ao qual o sistema ficará instalado, isto é, a pasta até o local onde se encontram todos os arquivos que serão gerados. Você poderá alterar este caminho, mas é recomendável que seja mantido, afim de termos sua instalação completa e conforme está nesse passo a passo. Portanto clique no botão indicado na imagem, `Seguinte`, e posterior o botão `Finalizar`.

Pronto, com estes passos temos instalado o sistema exTrato em nosso sistema operacional.

## Dicas sobre o exTrato

Nesta seção contém algumas dicas sobre programas que foram instalados, formas de contornar possíveis problemas, e facilitadores de modo geral.

### Como abrir os arquivos FXML

- Para que este procedimento seja executado com sucesso, primeiramente tenha instalado **JavaFX Scene Builder** em sua máquina. Com isto feito, se torna algo simples, apenas dê duplo click no documento que queira editar. De imediato será aberto o **JavaFX Scene Builder**, nele você poderá editar todo o conteúdo que julgar necessário.

### Importar bibliotecas para o Scene Builder

- Bastante útil caso queira compor em seu projeto um novo visual, um novo layout, componentes numa nova temática. Para que isso seja possível é feito a importação de bibliotecas no programa.
- Basta que seja feito da seguinte forma:

```
Library > Import JAR/FXML File...
```
- Irá abrir a tela ao qual você deverá procurar e selecionar o `.JAR` escolhido.

## Tecnologias utilizadas

* **[Netbeans](https://maven.apache.org/)** - Ambiente de desenvolvimento de software
* **[Apache Derby](https://db.apache.org/derby/)** - é um sistema de gerenciamento de banco de dados relacional Java que pode ser embutido em programas Java e usado para processamento de transações online.
* **[Maven](https://maven.apache.org/)** - Gerenciador de Dependências
* **[JPA](https://pt.wikipedia.org/wiki/Java_Persistence_API)** - Java Persistence API (ou simplesmente JPA) é uma API padrão da linguagem Java que descreve uma interface comum para frameworks de persistência de dados.
* **[JavaFX Scene Builder](http://hibernate.org/)** -  Ambiente de desenvolvimento de interface do sistema.

## Versão

* `exTrato v.1.0.0` - É a versão atual do sistema. Onde significa respectivamente:
    - `1` - Versão atual do projeto;
    - `0` - Release do projeto;
    - `0` - Revisões no projeto.


## Autores

* **Thales Tassi** - *Analista desenvolvedor responsável pelo sistema exTrato* - [Github](https://github.com/tassithales)
* **Vitor Hugo Prado** - *Analista desenvolvedor de Produtos e supervisor do sistema exTrato* - [Github](https://github.com/vitprado)
* **Rodrigo Aznar** - *Gerente de desenvolvimento* - [Github](https://github.com/rodrigo-aznar)


## Direitos Autorais e Linceças de Uso

* Este projeto está licenciado, e todos os direitos autorais referentes são da total domínio da empresa **Exacta Balanças**. Para maiores informações consulte o [site](http://www.exactabalancas.com.br/).