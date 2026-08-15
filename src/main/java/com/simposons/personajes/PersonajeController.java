package com.simposons.personajes;

import com.simposons.personajes.model.Personaje;
import com.simposons.personajes.service.SimpsonsService;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.List;

public class PersonajeController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private ListView<Personaje> personajesList;
    @FXML
    private Label nombreLabel;
    @FXML
    private Label ocupacionLabel;
    @FXML
    private Label citaLabel;
    @FXML
    private ImageView fotoImageView;
    @FXML
    private TextArea detallesArea;

    private SimpsonsService service;

    @FXML
    public void initialize() {
        service = new SimpsonsService();
        
        personajesList.setOnMouseClicked(event -> mostrarDetalles());
        searchButton.setOnAction(event -> buscarPersonaje());
    }

    @FXML
    private void buscarPersonaje() {
        String nombre = searchField.getText().trim();
        if (nombre.isEmpty()) {
            mostrarAlerta("Por favor ingresa un nombre");
            return;
        }

        try {
            List<Personaje> personajes = service.buscarPersonajes(nombre);
            if (personajes.isEmpty()) {
                mostrarAlerta("No se encontraron personajes con ese nombre");
            } else {
                personajesList.getItems().clear();
                personajesList.getItems().addAll(personajes);
            }
        } catch (Exception e) {
            mostrarAlerta("Error al buscar: " + e.getMessage());
        }
    }

    @FXML
    private void mostrarDetalles() {
        Personaje personaje = personajesList.getSelectionModel().getSelectedItem();
        if (personaje != null) {
            nombreLabel.setText(personaje.getNombre());
            ocupacionLabel.setText(personaje.getOcupacion());
            citaLabel.setText(personaje.getCita());
            
            if (personaje.getImagen() != null && !personaje.getImagen().isEmpty()) {
                try {
                    Image imagen = new Image(personaje.getImagen(), 200, 300, true, true);
                    fotoImageView.setImage(imagen);
                } catch (Exception e) {
                    fotoImageView.setImage(null);
                }
            }
            
            detallesArea.setText("Género: " + personaje.getGenero() + "\n" +
                               "Edad: " + personaje.getEdad() + "\n" +
                               "Estado: " + personaje.getEstado());
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
