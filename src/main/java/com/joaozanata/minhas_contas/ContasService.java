package com.joaozanata.minhas_contas;

import java.util.List;

public interface ContasService {
    public void salvar(Conta conta);
    public List<Conta> buscarTodas();
    public void apagar(int id);
    public void atualizar(Conta conta);

    public static ContasService getNewInstance() {
        return new ContasCSVService();
    }
}
