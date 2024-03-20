## Descrição do Projeto

Este projeto é uma API RESTful para gerenciar uma lista de tarefas (ToDo list). Ele permite a criação, leitura, atualização e exclusão de tarefas, bem como a marcação de tarefas como concluídas.

A API suporta as seguintes operações:

1. **Criar Tarefa**: Permite a criação de uma nova tarefa especificando o título, descrição, data de vencimento e prioridade da tarefa.

2. **Listar Tarefas**: Recupera uma lista de todas as tarefas cadastradas.

3. **Atualizar Tarefa**: Atualiza os detalhes de uma tarefa existente, incluindo título, descrição e/ou data de vencimento.

4. **Marcar Tarefa como Concluída**: Marca uma tarefa como concluída, atualizando o status da tarefa para concluída e registrando a data e hora em que a tarefa foi concluída.

5. **Excluir Tarefa**: Remove uma tarefa existente da lista de tarefas.

## Endpoints Disponíveis

- **POST /tasks**: Cria uma nova tarefa.
- **GET /tasks**: Lista todas as tarefas cadastradas.
- **PUT /tasks/{taskId}**: Atualiza os detalhes de uma tarefa existente.
- **PUT /tasks/complete/{taskId}**: Marca uma tarefa como concluída.
- **DELETE /tasks/{taskId}**: Exclui uma tarefa existente.

## Modelo de Dados

A entidade `Task` possui os seguintes campos:

- `id` (UUID): Identificador único da tarefa.
- `title` (String): Título da tarefa.
- `description` (String): Descrição da tarefa.
- `dueDate` (Date): Data de vencimento da tarefa.
- `priority` (String): Prioridade da tarefa (Alta, Média, Baixa).
- `completed` (Boolean): Indica se a tarefa foi concluída.
- `completedAt` (Date): Data e hora em que a tarefa foi concluída.

## Autenticação

A API não requer autenticação para operações básicas de CRUD. No entanto, algumas operações podem exigir autenticação, dependendo dos requisitos de segurança do sistema.

## Formato de Requisição e Resposta

A API aceita requisições e responde em formato JSON.