# Gestão Hospitalar :hospital:

Você agora irá atuar em um projeto para um software de Gestão Hospitalar. Você será responsável por desenvolver API para atender aos seguintes requisitos:

- [x] CRUD médico. Obs: Sistema deve consistir e garantir que um médico que já realizou algum atendimento não pode ser excluído;
- [x] CRUD paciente. Obs: Sistema deve consistir e garantir que um paciente que possui atendimento não possa ser excluído;
- [x] Inserir novos atendimentos;
- [x] Editar atendimentos;
- [x] Listar os atendimentos entre um período de datas;
- [x] Listar médicos que trabalharam em um período de datas;
- [x] Listar os pacientes de um determinado médico;
- [x] Listar todos médicos que atenderam um determinado paciente;
- [x] Gerar um pdf com todos pacientes;

## Plus:

Foi tranquilo até aqui? Então mostre suas habilidades e vá além, mostre que você absorveu bem o conteúdo, permita que seu sistema apenas responda as requisições autenticadas.

## Entrega:

- Código fonte
- Exportação do Postman (Todos endpoints devem estar testados pelo postman)
- Base de dados deve estar hospedada em algum domínio
- Projeto com uso de maven

Campos mínimos de cada tabela:

| Paciente           | Médico             | Atendimento         |
| ------------------ | ------------------ | ------------------- |
| Identificador      | Identificador      | Identificador       |
| Nome               | Nome               | Data de atendimento |
| CPF                | CPF                | Médico              |
| Data de nascimento | CRM                | Paciente            |
| Sexo               | Data de nascimento | Observação          |
|                    | Sexo               | Ativo/Inativo       |

## Como rodar o projeto:

- Faça um clone do projeto e abra na usa IDE de preferência
- Rode o projeto com o comando `mvn spring-boot:run -Dspring-boot.run.profiles=prod`
- Acesse a documentação da API em `http://localhost:8080/gestao-hospitalar/swagger-ui.html` para ver os endpoints disponíveis
- Importe o arquivo `gestao-hospitalar.postman_collection.json` no Postman para ter acesso aos endpoints já configurados
