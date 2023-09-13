# RH Gerenciamento
Este é um sistema simples de gerenciamento de escola/faculdade desenvolvido em Kotlin com o uso de banco de dados PostgreSQL. O sistema permite a gestão de cursos, professores, alunos e matrículas em cursos.
## Funcionalidades
O sistema inclui as seguintes funcionalidades:

- Cadastro e gerenciamento de cursos.
- Cadastro e gerenciamento de professores.
- Cadastro e gerenciamento de alunos.
- Matrícula e desmatrícula de alunos em cursos.
- Consultas de alunos matriculados em cursos, cursos ministrados por professores e muito mais.


## Como Executar
Para executar  Biblioteca SimCity, siga as instruções abaixo:
### Passos para Execução
Para executar o sistema, siga estas etapas:

1. Clone o repositório para o seu ambiente local.
2. Configure o banco de dados PostgreSQL de acordo com as configurações no arquivo `application.properties`.
3. Abra o projeto em sua IDE preferida (como IntelliJ IDEA ou Eclipse).
4. Execute o aplicativo principal, que geralmente é encontrado em `src/main/kotlin/com/exemplo/Main.kt`.

## Linguagem de uso: **Kotlin**
Um trecho de código escrito em Kotlin:
~~~kotlin
val statement = connection.createStatement()
val resultSet = statement.executeQuery(sql)
var coursesFound = false

while (resultSet.next()) {
    val courseName = resultSet.getString("name_courses")
    println("Curso sem alunos matriculados: $courseName")
    coursesFound = true
}
~~~
### **Pessoa Autora**
Raissa Vicente Dias
* [GitHub](https://github.com/RaiVD)
* [Linkedin](https://www.linkedin.com/mwlite/in/raissa-vicente-86a3b2210)