# Sale-System-v2
* Sistema PDV para uma adega

# Linguagens usadas
* Java - versão 11
* MySQL
* XML

# Ambiente

* Você deve ter instalado a versão 11 do Java
* MySQL - seja ele baixado ou container
* Além disso deve baixar o sdk do JavaFx - https://gluonhq.com/products/javafx/ e o mysql connector em formato zip (platform independent) -> https://downloads.mysql.com/archives/c-j/
* Ambos os arquivos que foram baixados em formato zip devem der descompactados e deixados em uma pasta que for melhor para você
* É recomendável que você tenha uma IDE própria para o Java como Eclipse ou Intellij

# Importando o projeto no eclipse

* Abra a pasta clonada como workspace no eclipse e após isso vá em project explorer e clique em import projects

<h1 align="center">
    <img alt="Passo 1" src="https://github.com/BlackWidow29/Sale-System-v2/blob/512dcfbc129895ea2337298a963d0e59532710e7/.github/Captura%20de%20tela%20de%202020-12-30%2010.52.53.png" width="500px">
</h1>

* Vá em general e selecione a opção existing projects into workspace

<h1 align="center">
    <img alt="Passo 2" src="https://github.com/BlackWidow29/Sale-System-v2/blob/512dcfbc129895ea2337298a963d0e59532710e7/.github/Captura%20de%20tela%20de%202020-12-30%2010.53.08.png" width="500px">
</h1>

* Após isso selecione a opção select root directory e depois clique em browse

<h1 align="center">
    <img alt="Passo 3" src="https://github.com/BlackWidow29/Sale-System-v2/blob/512dcfbc129895ea2337298a963d0e59532710e7/.github/Captura%20de%20tela%20de%202020-12-30%2010.53.40.png" width="500px">
</h1>

* Selecione a pasta inteira do projeto - Sale-System-v2

<h1 align="center">
    <img alt="Passo 4" src="https://github.com/BlackWidow29/Sale-System-v2/blob/512dcfbc129895ea2337298a963d0e59532710e7/.github/Captura%20de%20tela%20de%202020-12-30%2010.54.17.png" width="500px">
</h1>


* Ele vai detectar todos os projetos que compoem o sistema e caso ele não selecione, clique em select all e depois em finish

<h1 align="center">
    <img alt="Passo 5" src="https://github.com/BlackWidow29/Sale-System-v2/blob/512dcfbc129895ea2337298a963d0e59532710e7/.github/Captura%20de%20tela%20de%202020-12-30%2010.54.29.png" width="500px">
</h1>

* Após ele terminar a importação, clique no botão direito na pasta app-interface e vá na opção build path -> configure build path

<h1 align="center">
    <img alt="Passo 6" src="https://github.com/BlackWidow29/Sale-System-v2/blob/512dcfbc129895ea2337298a963d0e59532710e7/.github/Captura%20de%20tela%20de%202020-12-30%2010.55.34.png" width="1000px">
</h1>

* Vá na opção libraries e na opção module path remova todas e reimporte os jars que estão na pasta da jdk/lib ou selecione um jar por vez e selecione oa opção edit

<h1 align="center">
    <img alt="Passo 7" src="https://github.com/BlackWidow29/Sale-System-v2/blob/512dcfbc129895ea2337298a963d0e59532710e7/.github/Captura%20de%20tela%20de%202020-12-30%2010.57.57.png" width="1000px">
</h1>

* Selecione o jar que deseja substituir:

<h1 align="center">
    <img alt="Passo 8" src="https://github.com/BlackWidow29/Sale-System-v2/blob/512dcfbc129895ea2337298a963d0e59532710e7/.github/Captura%20de%20tela%20de%202020-12-30%2010.58.40.png" width="1000px">
</h1>

* Faça o mesmo com o mysql connector.

# Rodando o banco de dados

* O script do banco de dados se encontra em database/database.sql
* Após rodar esse script rode essa query -> INSERT INTO tbllogin (nmEmail,nmUser,nmPassword,dsType,dsStatus) VALUES ('administrador@gmail.com','administrador','1234567','Administrador','Ativo');
* A conexão configurada no sistema esta localizada em > app-repositories/src/br/com/mnbebidas/connection/ConnectionJDBC.java, caso precise mudar o usuario e senha fique a vontade.


# Rodando a aplicação

* O programa principal está localizado em > app-interface/src/application/Views/Login.java
* Para rodar a aplicação abra o arquivo clique com o botão direito e selecione a opção run as > run configurations


<h1 align="center">
    <img alt="Passo 9" src="https://github.com/BlackWidow29/Sale-System-v2/blob/master/.github/Captura%20de%20tela%20de%202020-12-30%2013.42.12.png" width="1000px">
</h1>

* Depois vá na opção arguments > vm arguments e cole este comando > --module-path path/to/javafx-sdk-11.0.2/lib --add-modules javafx.controls,javafx.fxml (você deve alterar o caminho da sdk do javafx)

<h1 align="center">
    <img alt="Passo 9" src="https://github.com/BlackWidow29/Sale-System-v2/blob/master/.github/Captura%20de%20tela%20de%202020-12-30%2013.48.24.png" width="1000px">
</h1>

* Após essas configurações o programa deve rodar normalmente
