import java.util.ArrayList;
import java.util.List;

//$ Representa o estoque da loja; TEM UMA lista de Product (composição), não herda
//$ de Product — o estoque não é um produto, ele guarda produtos.
public class Estoque {

    //v Lista de produtos cadastrados; aceita qualquer subtipo de Product graças ao polimorfismo.
    private List<Product> produtos;

    public Estoque() {
        this.produtos = new ArrayList<>();
    }

    public void adicionarProduto(Product p) {
        produtos.add(p);
    }

    //f Delega a venda para o produto correspondente e deixa a exceção subir
    //f (propagar) para quem chamou, sem tratar aqui.
    //p indice: posição do produto na lista.
    //p quantidade: quantidade a vender.
    public void venderProduto(int indice, int quantidade) throws ProdutoIndisponivelException {
        validarIndice(indice);
        produtos.get(indice).vender(quantidade);
    }

    //f Soma o valor de cada produto sem precisar saber se é ProdutoComum ou
    //f ProdutoPerecivel — é aqui que o polimorfismo dinâmico de calcularValorTotal() aparece.
    //r Valor total de todos os produtos do estoque.
    public double calcularValorTotalEstoque() {
        double total = 0;
        //! Cada chamada a calcularValorTotal() resolve dinamicamente para a
        //! implementação da subclasse real do produto.
        for (Product p : produtos) {
            total += p.calcularValorTotal();
        }
        return total;
    }

    public void listarProdutos() {
        for (int i = 0; i < produtos.size(); i++) {
            System.out.println("[" + i + "] " + produtos.get(i).getDescricao());
        }
    }

    public List<Product> getProdutos() {
        return produtos;
    }

    //f Evita IndexOutOfBoundsException "cru": centraliza a validação de índice
    //f antes de acessar a lista.
    private void validarIndice(int indice) {
        if (indice < 0 || indice >= produtos.size()) {
            throw new IndexOutOfBoundsException("Índice de produto inválido: " + indice);
        }
    }
}