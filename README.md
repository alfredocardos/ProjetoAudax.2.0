# ProjetoAudax.2.0
Sistema realizado para que a entidade 'Usuarui' realize o cadastro de artigos no banco de dados MYSQL.Projeto realizado usando O framework Spring Boot JPA, SECURITY, WEB ,TEST, ETC
Criação de uma API para realizar cadastros de artigos

Aplicação deverá conter
• CRUD de usuários
• CRUD de artigos
Essencial:

• Utilizar POO
Framework/biblioteca desejável para Java:

• Spring Boot
• Spring Data JPA

*****Regras de negócios******

Usuário:
• ‘username’ é um campo obrigatório
• o tamanho mínimo para o campo ‘username’ é de 3 caracteres
• o tamanho máximo para o campo ‘username’ é 150 caracteres
• o campo ‘username’ tem que ser único, logo não podem ter dois usuários com ele
• ‘password’ é um campo obrigatório
• o tamanho mínimo para o campo ‘password’ é 8 caracteres
• no campo ‘password’ deve ser utilizado algum algoritmo de criação de hash para
senha no momento de ser salvo
• o campo ‘uuid’ deve ser gerado pela aplicação na versão 4
• ao ser salvo deve ser guardado a data e o horário no campo ‘registeredAt’
• o campo ‘registeredAt’ não pode ser alterado
• o campo ‘registeredAt’ deve ser exibido no formato ISO8601
• um usuário que tenha artigos cadastrados não pode ser excluído

Artigo:
• ‘title’ é um campo obrigatório
• o tamanho mínimo para o campo ‘title’ é de 30 caracteres
• o tamanho máximo para o campo ‘title’ é de 70 caracteres
• o campo ‘title’ tem que ser único, logo não podem ter dois artigos com ele
• ‘resume’ é um campo obrigatório
• o tamanho mínimo para o campo ‘resume’ é de 50 caracteres
• o tamanho máximo para o campo ‘resume’ é de 100 caracteres
• ‘text’ é um campo obrigatório
• o tamanho mínimo para o campo ‘text’ é de 200 caracteres
• o campo ‘slug’ deve ser gerado a partir do campo ‘title’
• o campo ‘uuid’ deve ser gerado pela aplicação na versão 4
• ao ser salvo deve ser guardado a data e o horário no campo ‘registeredAt’
• o campo ‘registeredAt’ não pode ser alterado
• o campo ‘registeredAt’ deve ser exibido no formato ISO8601
