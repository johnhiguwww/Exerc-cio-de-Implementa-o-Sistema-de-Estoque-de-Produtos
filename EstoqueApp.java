//$ Classe de entrada do programa (main); monta um cenário de demonstração
//$ cobrindo cadastro, exceções, venda e cálculo do valor total do estoque.
import java.util.Locale;

public class EstoqueApp {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("pt", "BR"));
        Estoque estoque = new Estoque();

        // ---- 1. Cadastro de produtos válidos (2 comuns + 2 peréciveis) ----
        //f Cadastra os produtos-base do cenário; um dos peréciveis tem
        //f diasParaVencer <= 3 para acionar o desconto automático.
        try {
            estoque.adicionarProduto(new ProdutoComum("Arroz 5kg", 25.00, 50));
            estoque.adicionarProduto(new ProdutoComum("Detergente", 3.50, 100));
            estoque.adicionarProduto(new ProdutoPerecivel("Leite 1L", 4.80, 30, 10));
            estoque.adicionarProduto(new ProdutoPerecivel("Iogurte", 6.00, 20, 2)); // <= 3 dias: desconto
        } catch (QuantidadeInvalidaException e) {
            // Não deve acontecer aqui, pois os valores são válidos.
            System.out.println("Erro inesperado ao cadastrar produto válido: " + e.getMessage());
        }

        System.out.println("=== Produtos cadastrados ===");
        estoque.listarProdutos();

        // ---- 2. Tentativa de cadastro com quantidade negativa ----
        //! Demonstra QuantidadeInvalidaException sendo lançada e capturada corretamente.
        System.out.println("\n=== Tentando cadastrar produto com quantidade negativa ===");
        try {
            Product produtoInvalido = new ProdutoComum("Produto Fantasma", 10.00, -5);
            estoque.adicionarProduto(produtoInvalido);
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Falha ao cadastrar: " + e.getMessage());
        }

        // ---- 3. Venda válida ----
        System.out.println("\n=== Venda válida (10 unidades de Arroz 5kg) ===");
        try {
            estoque.venderProduto(0, 10);
            System.out.println("Venda realizada com sucesso!");
            System.out.println("Novo estado: " + estoque.getProdutos().get(0).getDescricao());
        } catch (ProdutoIndisponivelException e) {
            System.out.println("Falha na venda: " + e.getMessage());
        }

        // ---- 4. Tentativa de venda maior do que o disponível ----
        //! Demonstra ProdutoIndisponivelException sendo lançada e capturada corretamente.
        System.out.println("\n=== Tentando vender 1000 unidades de Arroz 5kg (estoque insuficiente) ===");
        try {
            estoque.venderProduto(0, 1000);
        } catch (ProdutoIndisponivelException e) {
            System.out.println("Falha na venda: " + e.getMessage());
        }

        // ---- 5. Bloco com os três catches, na ordem correta (mais específico -> mais genérico) ----
        System.out.println("\n=== Demonstração de bloco try com múltiplos catches ===");
        try {
            Product p = new ProdutoComum("Produto Teste", 15.00, 5); // pode lançar QuantidadeInvalidaException
            estoque.adicionarProduto(p);
            venderComTratamentoGenerico(p, 100); // pode lançar EstoqueException (ou subtipos)
        } catch (QuantidadeInvalidaException e) {
            System.out.println("Quantidade inválida: " + e.getMessage());
        } catch (ProdutoIndisponivelException e) {
            System.out.println("Produto indisponível: " + e.getMessage());
        } catch (EstoqueException e) {
            //!! A mais genérica precisa vir por último: se viesse antes, os catches
            //!! seguintes ficariam inalcançáveis e o código não compilaria.
            System.out.println("Erro genérico de estoque: " + e.getMessage());
        }

        // ---- 6. Valor total do estoque (mostra o polimorfismo em ação) ----
        System.out.println("\n=== Valor total do estoque ===");
        System.out.printf("R$ %.2f%n", estoque.calcularValorTotalEstoque());

        // ---- 7. Demonstração da sobrecarga aplicarDesconto (polimorfismo estático) ----
        System.out.println("\n=== Demonstração de aplicarDesconto (sobrecarga) ===");
        Product arroz = estoque.getProdutos().get(0);
        System.out.printf("Preço do Arroz antes: R$ %.2f%n", arroz.getPreco());
        arroz.aplicarDesconto(10); // versão sem teto
        System.out.printf("Preço após 10%% de desconto: R$ %.2f%n", arroz.getPreco());
        arroz.aplicarDesconto(50, 2.00); // versão com teto de R$2,00
        System.out.printf("Preço após 50%% de desconto com teto de R$2,00: R$ %.2f%n", arroz.getPreco());
    }

    //f Método auxiliar declarado com a exceção mais genérica (throws EstoqueException)
    //f para tornar o catch genérico do item 5 legitimamente alcançável do ponto de
    //f vista do compilador.
    //p p: produto a ser vendido.
    //p quantidade: quantidade a vender.
    //! Sem esse método, o catch(EstoqueException) seria avisado como código morto,
    //! pois só os subtipos específicos são lançados diretamente.
    private static void venderComTratamentoGenerico(Product p, int quantidade) throws EstoqueException {
        p.vender(quantidade);
    }
}