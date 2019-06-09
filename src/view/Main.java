package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.MyModel;
import viewModel.ViewModel;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        MyModel m=new MyModel();
        ViewModel vm=new ViewModel(m);
        m.addObserver(vm);
        FXMLLoader loader=new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root=loader.load();
        ViewController ctrl=loader.getController();
        ctrl.setViewModel(vm);
        vm.addObserver(ctrl);
       // Parent root = fxl.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("FlightGear Desktop App");
        primaryStage.setScene(new Scene(root, 1244, 422));
        primaryStage.show();



    }
}