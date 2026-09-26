//$ Exceção específica lançada quando preço ou quantidade informados na criação
//$ de um produto são negativos.
public class QuantidadeInvalidaException extends EstoqueException {

    //v Identificador de versão exigido pela convenção de classes Serializable.
    private static final long serialVersionUID = 1L;

    public QuantidadeInvalidaException(String mensagem) {
        super(mensagem);
    }
}