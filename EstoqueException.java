//$ Classe base da hierarquia de exceções do domínio de estoque; permite tratar
//$ qualquer erro relacionado a produtos ou estoque de forma genérica, quando necessário.
public class EstoqueException extends Exception {

    //f Repassa a mensagem para a superclasse Exception, mantendo o comportamento
    //f padrão de uma exceção checada.
    //p mensagem: descrição do que deu errado.
    public EstoqueException(String mensagem) {
        super(mensagem);
    }
}