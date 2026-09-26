//$ Contrato para qualquer item que possa ser vendido; separa o comportamento de
//$ venda da hierarquia de Product, permitindo reaproveitamento futuro por outras classes.
public interface Vendavel {

    //f Reduz a quantidade em estoque do item vendido.
    //p quantidadeDesejada: quantas unidades o cliente quer comprar.
    //! Implementações devem lançar ProdutoIndisponivelException se quantidadeDesejada
    //! for maior que o estoque disponível.
    void vender(int quantidadeDesejada) throws ProdutoIndisponivelException;
}