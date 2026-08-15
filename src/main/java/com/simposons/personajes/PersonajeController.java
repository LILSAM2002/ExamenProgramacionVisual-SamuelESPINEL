package com.simposons.personajes;

import com.simposons.personajes.model.Personaje;
import com.simposons.personajes.service.SimpsonsService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.util.List;
import java.util.Random;

public class PersonajeController {
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button randomButton;
    @FXML
    private Button loadMoreButton;
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
    @FXML
    private Label errorLabel;

    private SimpsonsService service;
    private List<Personaje> allPersonajes;
    private int currentPage = 0;
    private static final int ITEMS_PER_PAGE = 10;

    @FXML
    public void initialize() {
        service = new SimpsonsService();
        allPersonajes = null;
        
        personajesList.setOnMouseClicked(event -> mostrarDetalles());
        searchButton.setOnAction(event -> buscarPersonaje());
        randomButton.setOnAction(event -> cargarAleatorio());
        loadMoreButton.setOnAction(event -> cargarMas());
        loadMoreButton.setDisable(true);
        errorLabel.setStyle("-fx-text-fill: #d9534f; -fx-font-weight: bold;");
        limpiarError();
    }

    // ============ ACTIVIDAD 3: Búsqueda por nombre ============
    @FXML
    private void buscarPersonaje() {
        String nombre = searchField.getText().trim();
        if (nombre.isEmpty()) {
            mostrarError("Por favor ingresa un nombre");
            return;
        }

        limpiarError();
        loadMoreButton.setDisable(true);
        currentPage = 0;

        try {
            allPersonajes = service.buscarPersonajes(nombre);
            if (allPersonajes.isEmpty()) {
                mostrarError("No se encontraron personajes con ese nombre");
                personajesList.getItems().clear();
            } else {
                mostrarPagina(0);
                if (allPersonajes.size() > ITEMS_PER_PAGE) {
                    loadMoreButton.setDisable(false);
                }
            }
        } catch (Exception e) {
            mostrarError("Error al buscar: " + e.getMessage());
        }
    }

    // ============ ACTIVIDAD 2: Botón "➕ Cargar Más" (paginación) ============
    @FXML
    private void cargarMas() {
        if (allPersonajes != null) {
            currentPage++;
            mostrarPagina(currentPage);
            
            // Deshabilitar si llegamos al final
            int start = currentPage * ITEMS_PER_PAGE;
            if (start + ITEMS_PER_PAGE >= allPersonajes.size()) {
                loadMoreButton.setDisable(true);
            }
        }
    }

    private void mostrarPagina(int page) {
        if (allPersonajes == null || allPersonajes.isEmpty()) {
            return;
        }
        
        personajesList.getItems().clear();
        int start = page * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, allPersonajes.size());
        
        List<Personaje> pageItems = allPersonajes.subList(start, end);
        personajesList.getItems().addAll(pageItems);
    }

    // ============ ACTIVIDAD 4: Botón "🎲 Aleatorio" ============
    @FXML
    private void cargarAleatorio() {
        limpiarError();
        loadMoreButton.setDisable(true);
        currentPage = 0;

        try {
            // Usar nombres comunes de Los Simpsons
            String[] personajesAleatorios = {
                "Homer", "Marge", "Bart", "Lisa", "Maggie",
                "Mr. Burns", "Ned", "Moe", "Barney", "Lenny",
                "Carl", "Krusty", "Krusty the Clown", "Skinner", "Flanders"
            };
            
            Random random = new Random();
            String personajeAleatorio = personajesAleatorios[random.nextInt(personajesAleatorios.length)];
            
            allPersonajes = service.buscarPersonajes(personajeAleatorio);
            if (allPersonajes.isEmpty()) {
                mostrarError("No se encontró personaje aleatorio. Intenta de nuevo.");
            } else {
                mostrarPagina(0);
                if (allPersonajes.size() > ITEMS_PER_PAGE) {
                    loadMoreButton.setDisable(false);
                }
            }
        } catch (Exception e) {
            mostrarError("Error al cargar aleatorio: " + e.getMessage());
        }
    }

    // ============ ACTIVIDAD 1: Diálogo "Ver detalles" del personaje ============
    @FXML
    private void mostrarDetalles() {
        Personaje personaje = personajesList.getSelectionModel().getSelectedItem();
        if (personaje != null) {
            limpiarError();
            
            // Actualizar información en la UI principal
            nombreLabel.setText(personaje.getNombre());
            ocupacionLabel.setText(personaje.getOcupacion() != null ? personaje.getOcupacion() : "N/A");
            citaLabel.setText(personaje.getCita() != null ? personaje.getCita() : "N/A");
            
            if (personaje.getImagen() != null && !personaje.getImagen().isEmpty()) {
                try {
                    Image imagen = new Image(personaje.getImagen(), 200, 300, true, true);
                    fotoImageView.setImage(imagen);
                } catch (Exception e) {
                    fotoImageView.setImage(null);
                }
            } else {
                fotoImageView.setImage(null);
            }
            
            detallesArea.setText(
                "Nombre: " + (personaje.getNombre() != null ? personaje.getNombre() : "N/A") + "\n" +
                "Género: " + (personaje.getGenero() != null ? personaje.getGenero() : "N/A") + "\n" +
                "Edad: " + (personaje.getEdad() != null ? personaje.getEdad() : "N/A") + "\n" +
                "Ocupación: " + (personaje.getOcupacion() != null ? personaje.getOcupacion() : "N/A") + "\n" +
                "Estado: " + (personaje.getEstado() != null ? personaje.getEstado() : "N/A")
            );
            
            // ACTIVIDAD 1: Mostrar diálogo emergente con detalles
            mostrarDialogoDetalles(personaje);
        }
    }

    private void mostrarDialogoDetalles(Personaje personaje) {
        // Crear cuadro de diálogo
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Detalles del Personaje");
        dialog.setHeaderText(personaje.getNombre());

        // Crear contenido del diálogo
        VBox content = new VBox(10);
        content.setPrefWidth(400);
        content.setStyle("-fx-padding: 15;");
        content.setAlignment(Pos.TOP_LEFT);

        // Imagen
        ImageView dialogImage = new ImageView();
        dialogImage.setFitWidth(150);
        dialogImage.setFitHeight(200);
        dialogImage.setPreserveRatio(true);
        if (personaje.getImagen() != null && !personaje.getImagen().isEmpty()) {
            try {
                dialogImage.setImage(new Image(personaje.getImagen()));
            } catch (Exception e) {
                // Ignorar si no carga la imagen
            }
        }

        // Información en el diálogo
        Label lblNombre = new Label("Nombre: " + (personaje.getNombre() != null ? personaje.getNombre() : "N/A"));
        lblNombre.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
        
        Label lblGenero = new Label("Género: " + (personaje.getGenero() != null ? personaje.getGenero() : "N/A"));
        Label lblEdad = new Label("Edad: " + (personaje.getEdad() != null ? personaje.getEdad() : "N/A"));
        Label lblOcupacion = new Label("Ocupación: " + (personaje.getOcupacion() != null ? personaje.getOcupacion() : "N/A"));
        Label lblEstado = new Label("Estado: " + (personaje.getEstado() != null ? personaje.getEstado() : "N/A"));
        
        Label lblCita = new Label("Cita Famosa:");
        lblCita.setStyle("-fx-font-weight: bold;");
        
        TextArea textoCita = new TextArea(personaje.getCita() != null ? personaje.getCita() : "N/A");
        textoCita.setWrapText(true);
        textoCita.setPrefRowCount(3);
        textoCita.setEditable(false);

        // Agregar elementos al contenido
        content.getChildren().addAll(
            lblNombre,
            dialogImage,
            lblGenero,
            lblEdad,
            lblOcupacion,
            lblEstado,
            lblCita,
            textoCita
        );

        // Crear ScrollPane para permitir scroll si es necesario
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        
        dialog.getDialogPane().setContent(scrollPane);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
        
        // Mostrar el diálogo
        dialog.showAndWait();
    }

    // ============ ACTIVIDAD 5: Mensaje visual de error ============
    private void mostrarError(String mensaje) {
        errorLabel.setText("⚠️ " + mensaje);
        errorLabel.setVisible(true);
        errorLabel.setStyle("-fx-text-fill: #d9534f; -fx-font-weight: bold; -fx-font-size: 12;");
    }

    private void limpiarError() {
        errorLabel.setText("");
        errorLabel.setVisible(false);
    }
}
