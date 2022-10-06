package imc;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class IMC extends Application {
	private HBox PesoBox, AlturaBox, IMCBox;
	private TextField pesoField, alturaField;
	private Label IMC, estado, pesotext, kg, alturatext, cm, imctext;
	private VBox root;

	public void start(Stage primaryStage) throws Exception {
		IMC = new Label();
		estado = new Label();
		pesotext = new Label("Peso: ");
		kg = new Label("kg");
		alturatext = new Label("Altura: ");
		cm = new Label(" cm");
		imctext = new Label("IMC: ");

		pesoField = new TextField();
		pesoField.setPromptText("Introduce tu peso");
		alturaField = new TextField();
		alturaField.setPromptText("Introduce tu altura");

		PesoBox = new HBox(5, pesotext, pesoField, kg);
		AlturaBox = new HBox(5, alturatext, alturaField, cm);
		IMCBox = new HBox(5, imctext, IMC);
		root = new VBox(5, PesoBox, AlturaBox, IMCBox, estado);
		root.setFillWidth(false);
		root.setAlignment(Pos.CENTER);

		DoubleProperty altura = new SimpleDoubleProperty();
		DoubleProperty masa = new SimpleDoubleProperty();
		DoubleProperty imc = new SimpleDoubleProperty();
		StringProperty diagnostico = new SimpleStringProperty();
		alturaField.textProperty().bindBidirectional(altura, new NumberStringConverter());
		pesoField.textProperty().bindBidirectional(masa, new NumberStringConverter());

		imc.bind(masa.divide(altura.divide(100).multiply(altura.divide(100))));
		imc.addListener((o, ov, nv) -> {
			double i =nv.doubleValue();
			if(i < 18.5) {
				diagnostico.set("Bajo peso");
			}else if (18.5 <= i && i < 25) {
				diagnostico.set("Peso Normal");
			}else if(25<= i && i < 30){
				diagnostico.set("Sobrepeso");
			}else {
				diagnostico.set("Obesidad");
			}	
		});
		diagnostico.addListener((o, ov, nv) -> estado.setText(nv));
		
		Scene scene = new Scene(root, 300, 200);
		primaryStage.setTitle("CalculadoraIMC");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

}