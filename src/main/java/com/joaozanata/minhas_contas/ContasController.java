package com.joaozanata.minhas_contas;

import java.net.URL; // representa e manipula um endereço na internet
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId; //identifica e representa o fuso horário
import java.util.Date;
import java.util.ResourceBundle; // isola elementos específicos do idioma
// sincroniza as propriedades de 2 objetos sem precisar codar listeners
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

    // configura componentes visuais: popula a tabela, define textos iniciais e estilos, adiciona os listeners que monitoram
    // desde o início e faz chamadas ao arquivo CSV exibir assim que a janela abre
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        service = ContasService.getNewInstance();
        configuraColunas();
        configuraBindings();
        atualizaDadosTabela();
    }

    // métodos chamados ao clicar nos botões
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
    // adiciona os valores digitados pelo usuário ao objeto conta
    private void pegaValores(Conta c) {
        c.setConcessionaria(txtConsc.getText());
        c.setDescricao(txtDesc.getText());
        c.setDataVencimento(dataSelecionada());
    }
    // obtem a data selecionada e devolve a data com horário
    private Date dataSelecionada() {
        LocalDateTime time = dpVencimento.getValue().atStartOfDay();
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }
    // atualiza os dados da tabela e limpa os textFields
    private void atualizaDadosTabela() {
        tblContas.getItems().setAll(service.buscarTodas());
        limpar();
    }
    // extrai propriedades do objeto para exibir nas células da tabela
    private void configuraColunas() {
        clConsc.setCellValueFactory(new PropertyValueFactory<>("concessionaria"));
        clDesc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        clVenc.setCellValueFactory(new PropertyValueFactory<>("dataVencimento"));
    }
    // lógica da tela
    private void configuraBindings() {
        // é false quando os campos da tela estão preenchidos
        BooleanBinding camposPreenchidos = txtConsc.textProperty().isEmpty()
                .or(txtDesc.textProperty().isEmpty())
                .or(dpVencimento.valueProperty().isNull());
        // é false há algo selecionado na tabela
        BooleanBinding algoSelecionado = tblContas.getSelectionModel().selectedItemProperty().isNull();
        // habilita os botões abaixo se algo foi selecionado na lista da tabela
        btnApagar.disableProperty().bind(algoSelecionado);
        btnAtualizar.disableProperty().bind(algoSelecionado);
        btnLimpart.disableProperty().bind(algoSelecionado);
        // botão salvar é habilitado se houve seleção e os campos foram preenchidos
        btnSalvar.disableProperty().bind(algoSelecionado.not().or(camposPreenchidos));
        // quando algo é selecionado na tabela, exibe nos campos os valores atuais para a edição do usuário
        // 'o' é o valor antigo e 'n' é o novo valor
        tblContas.getSelectionModel().selectedItemProperty().addListener((b, o, n) -> {
            if (n != null) {
                LocalDate data = null;
                data = n.getDataVencimento().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                txtConsc.setText(n.getConcessionaria());
                txtDesc.setText(n.getDescricao());
                dpVencimento.setValue(data);
            }
        });
    }

}
