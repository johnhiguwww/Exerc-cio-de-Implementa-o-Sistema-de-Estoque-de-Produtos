//$ Classe abstrata que centraliza dados e regras comuns a qualquer produto vendável.
//! Força as subclasses a definir sua própria forma de calcular o valor total —
//! é a base do polimorfismo dinâmico usado por Estoque.
public abstract class Product implements Vendavel {

    //v Nome de exibição do produto.
    private String nome;
    //v Preço unitário atual; pode ser reduzido por aplicarDesconto().
    private double preco;
    //v Quantidade disponível em estoque; diminui a cada venda.
    private int quantidade;

    //f Valida os dados antes de criar o produto, evitando estados inconsistentes
    //f desde a origem.
    //p nome: nome de exibição do produto.
    //p preco: preço unitário inicial.
    //p quantidade: quantidade inicial em estoque.
    //! Lança QuantidadeInvalidaException se preco ou quantidade forem negativos.
    public Product(String nome, double preco, int quantidade) throws QuantidadeInvalidaException {
        //if Preço negativo não tem sentido de negócio: bloqueia antes de atribuir qualquer campo.
        if (preco < 0) {
            throw new QuantidadeInvalidaException(
                    "Preço não pode ser negativo. Valor informado: " + preco);
        }
        //if Mesma proteção para quantidade negativa.
        if (quantidade < 0) {
            throw new QuantidadeInvalidaException(
                    "Quantidade não pode ser negativa. Valor informado: " + quantidade);
        }
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    //f Cada subclasse decide sua própria regra de cálculo (ex.: desconto por vencimento).
    //r Valor total do produto no estoque, já considerando qualquer regra especial da subclasse.
    public abstract double calcularValorTotal();

    //f Descrição textual padrão; subclasses podem sobrescrever para acrescentar
    //f informações específicas (ex.: validade).
    //r String formatada com nome, preço e quantidade.
    public String getDescricao() {
        return String.format("%s | Preço: R$ %.2f | Quantidade: %d", nome, preco, quantidade);
    }

    //f Implementação de Vendavel: só baixa o estoque se houver quantidade suficiente.
    //p quantidadeDesejada: quantidade que se deseja vender.
    @Override
    public void vender(int quantidadeDesejada) throws ProdutoIndisponivelException {
        //if Não deixa vender mais do que existe em estoque.
        if (quantidadeDesejada > this.quantidade) {
            throw new ProdutoIndisponivelException(
                    "Estoque insuficiente para \"" + nome + "\". Disponível: " + this.quantidade
                            + ", solicitado: " + quantidadeDesejada);
        }
        this.quantidade -= quantidadeDesejada;
    }

    // ---- Sobrecarga (polimorfismo estático) ----

    //f Versão "sem teto": delega para a versão com dois parâmetros usando o próprio
    //f preço como limite máximo (equivale a não ter limite).
    //p percentual: percentual de desconto a aplicar sobre o preço atual.
    public void aplicarDesconto(double percentual) {
        aplicarDesconto(percentual, this.preco);
    }

    //f Versão com teto: aplica o mesmo cálculo, mas nunca abate mais do que descontoMaximo reais.
    //p percentual: percentual de desconto desejado.
    //p descontoMaximo: valor máximo, em reais, que pode ser abatido do preço.
    public void aplicarDesconto(double percentual, double descontoMaximo) {
        double desconto = this.preco * (percentual / 100.0);
        //if Se o desconto percentual passar do teto, usa o teto no lugar.
        if (desconto > descontoMaximo) {
            desconto = descontoMaximo;
        }
        this.preco -= desconto;
        //if Proteção extra: preço nunca deve ficar negativo por arredondamento ou teto mal configurado.
        if (this.preco < 0) {
            this.preco = 0;
        }
    }

    // ---- Getters usados pelas subclasses e por Estoque ----

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }
}