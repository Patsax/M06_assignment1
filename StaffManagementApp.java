import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import java.sql.*;

public class StaffManagementApp extends Application {
    private TextField tfID = new TextField();
    private TextField tfLastName = new TextField();
    private TextField tfFirstName = new TextField();
    private TextField tfMI = new TextField();
    private TextField tfAddress = new TextField();
    private TextField tfCity = new TextField();
    private TextField tfState = new TextField();
    private TextField tfTelephone = new TextField();
    private TextField tfEmail = new TextField();
    private Label lblStatus = new Label();
    
    @Override
    public void start(Stage primaryStage) {
        GridPane gridPane = new GridPane();
        gridPane.add(new Label("ID:"), 0, 0);
        gridPane.add(tfID, 1, 0);
        gridPane.add(new Label("Last Name:"), 0, 1);
        gridPane.add(tfLastName, 1, 1);
        gridPane.add(new Label("First Name:"), 0, 2);
        gridPane.add(tfFirstName, 1, 2);
        gridPane.add(new Label("MI:"), 0, 3);
        gridPane.add(tfMI, 1, 3);
        gridPane.add(new Label("Address:"), 0, 4);
        gridPane.add(tfAddress, 1, 4);
        gridPane.add(new Label("City:"), 0, 5);
        gridPane.add(tfCity, 1, 5);
        gridPane.add(new Label("State:"), 0, 6);
        gridPane.add(tfState, 1, 6);
        gridPane.add(new Label("Telephone:"), 0, 7);
        gridPane.add(tfTelephone, 1, 7);
        gridPane.add(new Label("Email:"), 0, 8);
        gridPane.add(tfEmail, 1, 8);
        gridPane.add(lblStatus, 1, 9);

        Button btnView = new Button("View");
        Button btnInsert = new Button("Insert");
        Button btnUpdate = new Button("Update");
        Button btnClear = new Button("Clear");

        gridPane.add(btnView, 0, 10); 
        gridPane.add(btnInsert, 1, 10); 
        gridPane.add(btnUpdate, 2, 10); 
        gridPane.add(btnClear, 3, 10); 

        btnView.setOnAction(e -> viewRecord()); 
        btnInsert.setOnAction(e -> insertRecord()); 
        btnUpdate.setOnAction(e -> updateRecord()); 
        btnClear.setOnAction(e -> clearFields()); 

        Scene scene = new Scene(gridPane, 400, 400); 

        primaryStage.setTitle("Staff Management"); 
        primaryStage.setScene(scene); 
        primaryStage.show(); 
    } 
    
    public static void main(String[] args) { launch(args); }

    private Connection connect() {
        String url = "jdbc:mysql://localhost:3306/yourDatabaseName";
        String user = "yourUsername"; String password = "yourPassword";
        try {
            return DriverManager.getConnection(url, user, password);
        }
        catch (SQLException e) {
            e.printStackTrace(); return null;
        }
    }

    private void viewRecord() { 
        String id = tfID.getText(); 
        String query = "SELECT * FROM Staff WHERE id = ?"; 

        try (Connection conn = connect(); 
        
        PreparedStatement pstmt = conn.prepareStatement(query)) { 
            pstmt.setString(1, id); 
            ResultSet rs = pstmt.executeQuery(); 
            
            if (rs.next()) { 
                tfLastName.setText(rs.getString("lastName")); 
                tfFirstName.setText(rs.getString("firstName")); 
                tfMI.setText(rs.getString("mi")); 
                tfAddress.setText(rs.getString("address")); 
                tfCity.setText(rs.getString("city")); 
                tfState.setText(rs.getString("state")); 
                tfTelephone.setText(rs.getString("telephone")); 
                tfEmail.setText(rs.getString("email")); 
                lblStatus.setText("Record found"); 
            } else { 
                lblStatus.setText("Record not found"); 
            } 
        } 
        catch (SQLException e) { 
            e.printStackTrace(); 
        } 
    }

    private void insertRecord() { 
        String query = "INSERT INTO Staff (id, lastName, firstName, mi, address, city, state, telephone, email) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"; 

        try (Connection conn = connect(); 
        
        PreparedStatement pstmt = conn.prepareStatement(query)) { 
            pstmt.setString(1, tfID.getText()); 
            pstmt.setString(2, tfLastName.getText()); 
            pstmt.setString(3, tfFirstName.getText()); 
            pstmt.setString(4, tfMI.getText()); 
            pstmt.setString(5, tfAddress.getText()); 
            pstmt.setString(6, tfCity.getText()); 
            pstmt.setString(7, tfState.getText()); 
            pstmt.setString(8, tfTelephone.getText()); 
            pstmt.setString(9, tfEmail.getText()); 
            pstmt.executeUpdate(); lblStatus.setText("Record inserted"); 
        } 
        catch (SQLException e) { 
            e.printStackTrace(); 
        } 
    }

    private void updateRecord() { 
        String query = "UPDATE Staff SET lastName = ?, firstName = ?, mi = ?, address = ?, city = ?, state = ?, telephone = ?, email = ? WHERE id = ?"; 
        
        try (Connection conn = connect(); 
        PreparedStatement pstmt = conn.prepareStatement(query)) { 
            pstmt.setString(1, tfLastName.getText()); 
            pstmt.setString(2, tfFirstName.getText()); 
            pstmt.setString(3, tfMI.getText()); 
            pstmt.setString(4, tfAddress.getText()); 
            pstmt.setString(5, tfCity.getText()); 
            pstmt.setString(6, tfState.getText()); 
            pstmt.setString(7, tfTelephone.getText()); 
            pstmt.setString(8, tfEmail.getText()); 
            pstmt.setString(9, tfID.getText()); 
            pstmt.executeUpdate(); lblStatus.setText("Record updated"); 
        } 
        catch (SQLException e) { 
            e.printStackTrace(); 
        } 
    }

    private void clearFields() { 
        tfID.clear(); 
        tfLastName.clear(); 
        tfFirstName.clear(); 
        tfMI.clear(); 
        tfAddress.clear(); 
        tfCity.clear(); 
        tfState.clear(); 
        tfTelephone.clear(); 
        tfEmail.clear(); 
        lblStatus.setText(""); 
    }

}
