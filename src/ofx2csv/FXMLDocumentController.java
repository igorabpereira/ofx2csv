/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ofx2csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author igora
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private MenuItem menuSelecionar;
    @FXML
    private MenuItem menuFechar;
    @FXML
    private MenuItem menuSobre;
    @FXML
    private Button botaoSelecionar;
    @FXML
    private TextField nomeArquivo;
    @FXML
    private TextField nomeArquivoDestino;
    @FXML
    private Button converter;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
            
    @FXML
    private void selecionar(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Arquivos .ofx", "*.ofx");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(botaoSelecionar.getScene().getWindow());
        if (file != null) {
            nomeArquivo.setText(file.getAbsolutePath());            
            nomeArquivoDestino.setText(file.getAbsolutePath().substring(0,file.getAbsolutePath().length()-4) + ".csv");
        } else {
            nomeArquivoDestino.setText("");
        }
    }

    @FXML
    private void fechar(ActionEvent event){
        Stage stage = (Stage) botaoSelecionar.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void sobre(ActionEvent event) {
    }

    @FXML
    private void converter(ActionEvent event) throws FileNotFoundException {
        if(! nomeArquivoDestino.getText().equals("")){
            try{
                Conversor.conversor(nomeArquivo.getText());
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Sucesso");
                alert.setHeaderText("Arquivo CSV gerado com sucesso.");
                alert.setContentText( nomeArquivoDestino.getText() );

                alert.showAndWait();

                nomeArquivo.setText("");
                nomeArquivoDestino.setText("");
                
            } catch (Exception e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText("Aconteceu um problema.");
                alert.setContentText( "É possível que o arquivo de origem e/ou destino estejam "
                        + "abertos por outro programa. Feche e tente novamente." );

                alert.showAndWait();
            }
        }
    }
    
}
