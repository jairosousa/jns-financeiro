package br.com.jns.financeiro.service.exceptions;

public class ViaCepException extends Throwable{
    private static final long serialVersionUID = 1L;
    private String CEP;
    private String Classe;

    /**
     * Gera uma nova exceção
     *
     * @param message descrição do erro
     * @param classe  classe da excessão original
     */
    public ViaCepException(String message, String classe) {
        super(message);

        this.CEP = "";
        this.Classe = classe;
    }

    /**
     * Gera uma nova exceção e define o CEP que foi solicitado
     *
     * @param message descrição do erro
     * @param cep     CEP que foi usado durante o processo
     * @param classe  classe da excessão original
     */
    public ViaCepException(String message, String cep, String classe) {
        super(message);

        this.CEP = cep;
        this.Classe = classe;
    }

    /**
     * Define o CEP da exceção
     *
     * @param cep
     */
    public void setCEP(String cep) {
        this.CEP = cep;
    }

    /**
     * Retorna o CEP da exceção
     *
     * @return String CEP
     */
    public String getCEP() {
        return this.CEP;
    }

    /**
     * Retorna se tem algum CEP
     *
     * @return boolean
     */
    public boolean hasCEP() {
        return !this.CEP.isEmpty();
    }

    /**
     * Retorna a classe da excessão original
     *
     * @return
     */
    public String getClasse() {
        return Classe;
    }
}
