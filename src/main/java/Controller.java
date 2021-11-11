import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Controller implements Initializable {

	// model

	private StringProperty asunto = new SimpleStringProperty();
	private BooleanProperty conexion = new SimpleBooleanProperty();
	private StringProperty contra = new SimpleStringProperty();
	private StringProperty emailD = new SimpleStringProperty();
	private StringProperty emailR = new SimpleStringProperty();
	private StringProperty mensaje = new SimpleStringProperty();
	private StringProperty nombre = new SimpleStringProperty();
	private StringProperty puerto = new SimpleStringProperty();

	// view

	@FXML
	private TextField asuntoText;

	@FXML
	private Button cerrarButton;

	@FXML
	private CheckBox conexionCheckbox;

	@FXML
	private PasswordField contraText;

	@FXML
	private TextField emaildText;

	@FXML
	private TextField emailrText;

	@FXML
	private Button enviarButton;

	@FXML
	private TextArea mensajeTextArea;

	@FXML
	private TextField nombreText;

	@FXML
	private TextField puertoText;

	@FXML
	private Button vaciarButton;

	@FXML
	private GridPane view;

	public Controller() throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/view.fxml"));
		loader.setController(this);
		loader.load();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		asunto.bind(asuntoText.textProperty());
		conexion.bind(conexionCheckbox.selectedProperty());
		contra.bind(contraText.textProperty());
		emailD.bind(emaildText.textProperty());
		emailR.bind(emailrText.textProperty());
		mensaje.bind(mensajeTextArea.textProperty());
		nombre.bind(nombreText.textProperty());
		puerto.bind(puertoText.textProperty());
	}

	public GridPane getView() {
		return view;
	}

	@FXML
	void onCerrarButton(ActionEvent event) {
		Stage stage = (Stage) cerrarButton.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onEnviarButton(ActionEvent event) {
		try {
			Email email = new SimpleEmail();
			email.setHostName(nombre.get());
			email.setSslSmtpPort(puerto.get());
			email.setAuthenticator(new DefaultAuthenticator(emailR.get(), contra.get()));
			email.setSSLOnConnect(conexion.get());
			email.setFrom(emailR.get());
			email.setSubject(asunto.get());
			email.setMsg(mensaje.get());
			email.addTo(emailD.get());
			email.send();
			
			Alert alertaB= new Alert(AlertType.INFORMATION);
			alertaB.setTitle("Mensaje enviado");
			alertaB.setHeaderText("Mensaje enviado con éxito a '" + emailD.get() + "'");
			alertaB.show();
			
			asuntoText.clear();
			mensajeTextArea.clear();
			emaildText.clear();
		} catch (EmailException e) {
			Alert alerta = new Alert(AlertType.ERROR);
			alerta.setContentText("Invalid message supplied");
			alerta.setTitle("Error");
			alerta.setHeaderText("No se pudo enviar el email");
			alerta.show();
			e.printStackTrace();
		}
		
	}

	@FXML
	void onVaciarButton(ActionEvent event) {
		asuntoText.clear();
		contraText.clear();
		emaildText.clear();
		emailrText.clear();
		mensajeTextArea.clear();
		nombreText.clear();
		puertoText.clear();
	}

}
