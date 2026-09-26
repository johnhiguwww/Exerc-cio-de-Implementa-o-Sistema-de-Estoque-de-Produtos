//$ Produto com prazo de validade; aplica desconto automático quando está próximo de vencer.
public class ProdutoPerecivel extends Product {

    //v Percentual de desconto aplicado quando o produto está próximo do vencimento.
    private static final double PERCENTUAL_DESCONTO_VENCIMENTO = 0.20; // 20%
    //v Quantidade de dias a partir da qual o desconto por vencimento passa a valer.
    private static final int LIMITE_DIAS_PARA_DESCONTO = 3;

    //v Dias restantes até o produto vencer; usado tanto no cálculo de valor quanto na descrição.
    private int diasParaVencer;

    public ProdutoPerecivel(String nome, double preco, int quantidade, int diasParaVencer)
            throws QuantidadeInvalidaException {
        super(nome, preco, quantidade);
        this.diasParaVencer = diasParaVencer;
    }

    //f Sobrescreve calcularValorTotal(): aplica 20% de desconto quando faltam poucos
    //f dias para vencer, além do cálculo padrão de preço × quantidade.
    @Override
    public double calcularValorTotal() {
        double valor = getPreco() * getQuantidade();
        //if Só aplica o desconto quando o produto está na janela de vencimento próximo.
        if (diasParaVencer <= LIMITE_DIAS_PARA_DESCONTO) {
            valor *= (1 - PERCENTUAL_DESCONTO_VENCIMENTO);
        }
        return valor;
    }

    //f Sobrescreve getDescricao() reaproveitando a versão da superclasse
    //f (super.getDescricao()) e complementando com a validade, evitando duplicar
    //f a formatação básica.
    @Override
    public String getDescricao() {
        String descontoInfo = diasParaVencer <= LIMITE_DIAS_PARA_DESCONTO
                ? " (20% de desconto por proximidade do vencimento)"
                : "";
        return super.getDescricao()
                + String.format(" | Vence em: %d dia(s)%s", diasParaVencer, descontoInfo);
    }

    public int getDiasParaVencer() {
        return diasParaVencer;
    }
}