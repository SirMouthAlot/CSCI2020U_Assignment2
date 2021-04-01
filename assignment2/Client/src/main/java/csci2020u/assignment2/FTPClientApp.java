package csci2020u.assignment2;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FTPClientApp extends Application 
{
    public HttpClient _client;

    public static String _dirPath;

    public ListView _serverDir = new ListView<>();
    public ListView _clientDir = new ListView<>();

    public ObservableList _serverFilesList = FXCollections.observableArrayList();
    public ObservableList _clientFilesList = FXCollections.observableArrayList();

    public static void main(String[] args) 
    {
        _dirPath = args[0];

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        //Inits client
        _client = new HttpClient("localhost", 8080, _dirPath);

        VBox root = new VBox();
        InitFileMenuBar(root);
        InitListViews(root);

        primaryStage.setTitle("File Transfer Application");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }

    private VBox InitFileMenuBar(VBox rootMenu)
    {
        //Create a top bar for menu items
        MenuBar barMenu = new MenuBar();
        //Create our file menu drop down
        Menu fileMenu = new Menu("File");
        //Add the filemenu to the filebar
        barMenu.getMenus().add(fileMenu);

        //Create our menu items
        //Sets up download button in menu
        MenuItem downloadFile = new MenuItem("Download File");
        downloadFile.setOnAction(e -> {
            DownloadSelectedFile();
        });

        //Sets up upload button in menu
        MenuItem uploadFile = new MenuItem("Upload File");
        uploadFile.setOnAction(e -> {
            UploadSelectedFile();
        });

        //Sets up refresh button in menu
        MenuItem refreshList = new MenuItem("Refresh File List");
        refreshList.setOnAction(e -> {
            RefreshListViews();
        });
        
        //Add the menu items to list
        fileMenu.getItems().addAll(downloadFile, uploadFile, refreshList);

        //Add the filebar to the rootmenu
        rootMenu.getChildren().add(barMenu);

        return rootMenu;
    }

    private VBox InitListViews(VBox rootMenu)
    {
        //Creates split pane for the list views
        SplitPane listBackground = new SplitPane();
        listBackground.getItems().addAll(_clientDir, _serverDir);

        //Sets up list
        RefreshListViews();

        //Add the listbackground to the root menu
        rootMenu.getChildren().add(listBackground);

        //Splitpane grows to fit vbox
        rootMenu.setVgrow(listBackground, Priority.ALWAYS);

        //Returns the root menu
        return rootMenu;
    }

    private void RefreshListViews()
    {
        //Set up the server List
        _serverFilesList.clear();
        _serverFilesList.addAll(_client.SendDirCommand());
        _serverDir.setItems(_serverFilesList);

        //Set up the client list
        _clientFilesList.clear();
        _clientFilesList.addAll(_client.GetDirList());
        _clientDir.setItems(_clientFilesList);
    }

    private void DownloadSelectedFile()
    {
        //Gets the name of the selected file
        String fileDir = (String)_serverFilesList.get(_serverDir.getSelectionModel().getSelectedIndex());

        //Downloads this file
        _client.SendDownloadCommand(fileDir);

        //Refreshes lists
        RefreshListViews();
    }

    private void UploadSelectedFile()
    {
        //Gets the name of the selected file
        String fileDir = (String)_clientFilesList.get(_clientDir.getSelectionModel().getSelectedIndex());

        //Uploads this file
        _client.SendUploadCommand(fileDir);

        //Refreshes the lists
        RefreshListViews();
    }
}
