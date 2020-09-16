package ba.unsa.etf.rpr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DrzavaController {
    public TextField fieldNaziv;
    public ChoiceBox<Grad> choiceGrad;
    public RadioButton tglDrzava;
    public RadioButton tglRepublika;
    public RadioButton tglKraljevina;
    private Drzava drzava;
    private ObservableList<Grad> listGradovi;

    public DrzavaController(Drzava drzava, ArrayList<Grad> gradovi) {
        this.drzava = drzava;
        listGradovi = FXCollections.observableArrayList(gradovi);
    }

    @FXML
    public void initialize() {
        choiceGrad.setItems(listGradovi);
        if (drzava != null) {
            fieldNaziv.setText(drzava.getPraviNaziv());
            if(drzava.getClass()==Republika.class)
                tglRepublika.setSelected(true);
            else if(drzava.getClass()==Kraljevina.class)
                tglKraljevina.setSelected(true);
            else
                tglDrzava.setSelected(true);
            //choiceGrad.getSelectionModel().select(drzava.getGlavniGrad());
            // ovo ne radi jer grad.getDrzava() nije identički jednak objekat kao član listDrzave

            for(int i=0; i < listGradovi.size(); i++)
                if (listGradovi.get(i).getId() == drzava.getGlavniGrad().getId())
                    choiceGrad.getSelectionModel().select(i);
        } else {
            choiceGrad.getSelectionModel().selectFirst();
            tglDrzava.setSelected(true);
        }
    }

    public Drzava getDrzava() {
        return drzava;
    }

    public void clickOk(ActionEvent actionEvent) {
        boolean sveOk = true;

        if (fieldNaziv.getText().trim().isEmpty()) {
            fieldNaziv.getStyleClass().removeAll("poljeIspravno");
            fieldNaziv.getStyleClass().add("poljeNijeIspravno");
            sveOk = false;
        } else {
            fieldNaziv.getStyleClass().removeAll("poljeNijeIspravno");
            fieldNaziv.getStyleClass().add("poljeIspravno");
        }

        if (!sveOk) return;

        if (drzava == null) drzava = new Drzava();
        if(tglRepublika.isSelected())
            drzava = new Republika(drzava.getId(),fieldNaziv.getText(),choiceGrad.getSelectionModel().getSelectedItem());
        else if(tglKraljevina.isSelected())
            drzava = new Kraljevina(drzava.getId(),fieldNaziv.getText(),choiceGrad.getSelectionModel().getSelectedItem());
        else
            drzava = new Drzava(drzava.getId(),fieldNaziv.getText(),choiceGrad.getSelectionModel().getSelectedItem());
        closeWindow();
    }

    public void clickCancel(ActionEvent actionEvent) {
        drzava = null;
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) fieldNaziv.getScene().getWindow();
        stage.close();
    }
}
