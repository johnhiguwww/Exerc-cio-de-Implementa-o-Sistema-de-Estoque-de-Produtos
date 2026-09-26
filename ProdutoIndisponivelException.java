//$ Exceção específica lançada quando se tenta vender mais unidades do que há
//$ disponível em estoque.
public class ProdutoIndisponivelException extends EstoqueException {

    //v Identificador de versão exigido pela convenção de classes Serializable.
    private static final long serialVersionUID = 1L;

    public ProdutoIndisponivelException(String mensagem) {
        super(mensagem);
    }
}