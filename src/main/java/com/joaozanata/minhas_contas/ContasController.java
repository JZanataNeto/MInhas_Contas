package com.joaozanata.minhas_contas;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class ContasController implements Initializable{
    @FXML
    private TableView<Conta> tblContas;
    @FXML
    private TableColumn<Conta, String> clConsc;
    @FXML
    private TableColumn<Conta, String> clDesc;
    @FXML
    private TableColumn<Conta, Date> clVenc;
    @FXML
    private TextField txtConsc;
    @FXML
    private TextField txtDesc;
    @FXML
    private DatePicker dpVencimento;
    @FXML
    private Button btnSalvar;
    @FXML
    private Button btnAtualizar;
    @FXML
    private Button btnApagar;
    @FXML
    private Button btnLimpart;

    private ContasService service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ContasService.getNewInstance();
        configuraColunas();
        configuraBindings();
        atualizaDadosTabela();
    }

    public void salvar() {
        Conta c = new Conta();
        pegaValores(c);
        service.salvar(c);
        atualizaDadosTabela();
    }
    public void atualizar() {
        Conta c = tblContas.getSelectionModel().getSelectedItem();
        pegaValores(c);
        service.atualizar(c);
        atualizaDadosTabela();
    }

    public void apagar() {
        Conta c = tblContas.getSelectionModel().getSelectedItem();
        service.apagar(c.getId());
        atualizaDadosTabela();
    }
    public void limpar() {
        tblContas.getSelectionModel().select(null);
        txtConsc.setText("");
        txtDesc.setText("");
        dpVencimento.setValue(null);
    }
    private void pegaValores(Conta c) {
        c.setConcessionaria(txtConsc.getText());
        c.setDescricao(txtDesc.getText());
        c.setDataVencimento(dataSelecionada());
    }
    private Date dataSelecionada() {
        LocalDateTime time = dpVencimento.getValue().atStartOfDay();
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }
    private void atualizaDadosTabela() {
        tblContas.getItems().setAll(service.buscarTodas());
        limpar();
    }
    private void configuraColunas() {
        clConsc.setCellValueFactory(new PropertyValueFactory<>("concessionaria"));
        clDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        clVenc.setCellValueFactory(new PropertyValueFactory<>("dataVencimento"));
    }
    private void configuraBindings() {
        // esse binding só e false quando os campos da tela estão preenchidos
        BooleanBinding camposPreenchidos = txtConsc.textProperty().isEmpty()
                .or(txtDesc.textProperty().isEmpty())
                .or(dpVencimento.valueProperty().isNull());
        // indica se há algo selecionado na tabela
        BooleanBinding algoSelecionado = tblContas.getSelectionModel().selectedItemProperty().isNull();
        // alguns botões só são habilitados se algo foi selecionado na tabela
        btnApagar.disableProperty().bind(algoSelecionado);
        btnAtualizar.disableProperty().bind(algoSelecionado);
        btnLimpart.disableProperty().bind(algoSelecionado);
        // o botão salvar só é habilitado se as informações foram preenchidas e não tem nada na tela
        btnSalvar.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
        // quando algo é selecionado na tabela, preenchemos os campos de entrada com os valores para o
        // usuário editar
        tblContas.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
            if (n != null) {
                LocalDate data = null;
                data = n.getDataVencimento().toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate();
                txtConsc.setText(n.getConcessionaria());
                txtDesc.setText(n.getDescricao());
                dpVencimento.setValue(data);
            }
        });
    }

}
