//$ Produto sem regras especiais de precificação; cálculo direto de preço × quantidade.
public class ProdutoComum extends Product {

    public ProdutoComum(String nome, double preco, int quantidade) throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
    }

    //f Sobrescreve o método abstrato de Product: aqui não há desconto, o valor
    //f total é simplesmente preço vezes quantidade.
    @Override
    public double calcularValorTotal() {
        return getPreco() * getQuantidade();
    }
}