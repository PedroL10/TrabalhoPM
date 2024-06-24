package codigo;

class Mesa {
    private static int contador = 1;

    private int IdMesa;

    private int quantidadeDeCadeiras;
    private boolean mesaOcupada;

    // remover requisicao daqui
    private Requisicao requisicaoAtual;
    private Pedido pedido;

    public Mesa(int quantidadeDeCadeiras) {
        this.IdMesa = contador;
        this.quantidadeDeCadeiras = quantidadeDeCadeiras;
        this.mesaOcupada = false;
        this.requisicaoAtual = null;
        this.pedido = null;
        contador++;
    }

    public int getQuantidadeDeCadeiras() {
        return quantidadeDeCadeiras;
    }

    public void setQuantidadeDeCadeiras(int quantidadeDeCadeiras) {
        this.quantidadeDeCadeiras = quantidadeDeCadeiras;
    }

    public Requisicao getRequisicaoAtual() {
        return requisicaoAtual;
    }

    public void setRequisicaoAtual(Requisicao requisicaoAtual) {
        this.requisicaoAtual = requisicaoAtual;
    }

    public int getIdMesa() {
        return IdMesa;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public boolean isMesaOcupada() {
        return mesaOcupada;
    }

    public void ocuparMesa() {
        mesaOcupada = true;
    }

    public void desocuparMesa() {
        mesaOcupada = false;
    }

}