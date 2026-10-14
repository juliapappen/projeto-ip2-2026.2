# Sistema AlimentaBem - Requisitos Funcionais

## 1. Cadastro de Doadores e Beneficiários
- **REQ01**: Cadastrar doadores, com herança para Pessoa Física e Empresa (Empresa possui CNPJ e razão social).
- **REQ02**: Cadastrar instituições beneficiárias (nome, CEP — completando endereço automaticamente via BrasilAPI, capacidade de atendimento mensal em famílias).

## 2. Alimentos e Doações
- **REQ03**: Cadastrar alimentos (nome, categoria: grãos/laticínios/hortifrúti/enlatados, unidade de medida).
- **REQ04**: Registrar doação vinculada a um doador, com itens de doação (composição) referenciando alimento, quantidade e data de validade.

## 3. Composição Nutricional (API Pública)
- **REQ05**: Consultar a API pública USDA FoodData Central (`https://api.nal.usda.gov/fdc/v1/foods/search`) para obter calorias e macronutrientes de cada alimento cadastrado.
- **REQ06**: Calcular o total de calorias e proteínas de uma doação com base nos itens recebidos.

## 4. Estoque e Distribuição
- **REQ07**: Controlar estoque de alimentos por lote (item de doação), ordenado por data de validade.
- **REQ08**: Registrar distribuição vinculando uma ou mais instituições beneficiárias e os itens retirados do estoque.
- **REQ09**: Aplicar automaticamente a lógica FIFO (First In, First Out) sugerindo os lotes mais próximos do vencimento para cada distribuição.

## 5. Relatórios
- **REQ10**: Relatório de doações recebidas por período e por categoria de alimento, exportável em CSV.
- **REQ11**: Relatório nutricional agregado (calorias/proteínas totais) distribuído por instituição beneficiária no mês.
- **REQ12**: Listar itens de estoque com vencimento nos próximos 7 dias.

## 6. Regras e Restrições
- **REQ13**: Bloquear a distribuição de qualquer item de estoque cuja data de validade já tenha expirado.
- **REQ14**: Não permitir o cadastro de instituição beneficiária com CEP inválido (sem retorno na consulta à API).
- **REQ15**: Impedir o registro de uma doação sem nenhum item vinculado.
- **REQ16**: Validar que a quantidade distribuída de um item não exceda a quantidade disponível em estoque.
- **REQ17**: Bloquear nova distribuição para uma instituição que já tenha atingido sua capacidade mensal de atendimento.
- **REQ18**: Garantir, ao sugerir lotes para distribuição, que o lote com validade mais próxima seja sempre oferecido primeiro (regra FIFO).
- **REQ19**: Bloquear o cadastro de doação com itens cuja data de validade já esteja vencida no momento do recebimento.
- **REQ20**: Impedir a exclusão de instituições beneficiárias que já possuam distribuições registradas.