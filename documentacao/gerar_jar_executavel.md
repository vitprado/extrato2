# Gerar JAR executável usando Eclipse

1. Adicionar o Persistencia.jar como dependência do projeto:
Project / Properties / Java Build Path / Libraries / 

![Passo 1](gerar_jar_img1.png)



1. "Add JARs..." selecionar o arquivo jar no projeto "persistencia/target/"

![Passo 2](gerar_jar_img2.png)



1. Fazer checkout no branch "release"
`git checkout release`


1. Clicar com o botão direito em "extratoVisualFX" e em seguida "Exportar..."

![Passo 3](gerar_jar_img3.png)



1. Selecionar "Java / Runnable JAR file"

![Passo 4](gerar_jar_img4.png)



1. Na tela seguinte "Extract required libraries into generated JAR" e "Finish"

![Passo 5](gerar_jar_img5.png)

