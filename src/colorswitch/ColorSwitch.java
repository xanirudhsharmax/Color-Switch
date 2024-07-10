package colorswitch;

import javafx.application.Application;
import javafx.scene.control.Hyperlink;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class ColorSwitch extends Application
{
    private Stage applicationWindow;
    private GameSpace currentGame;

    private static int gamesPlayed;
    private static double averageScore;
    private static int totalStarsCollected;
    private static int highScore;
    private static ArrayList<String> savedGames;

    private static final String recordsDirectory;
    private static final String statsLocation;

    private static final int maxRecords;

    static
    {
        recordsDirectory ="SavedRecords";
        statsLocation=recordsDirectory+"\\Stats.ser";

        maxRecords=5;

        gamesPlayed=0;
        averageScore=0;
        totalStarsCollected=0;
        highScore=0;
        savedGames=new ArrayList<>(maxRecords);
    }

    public static int getHighScore()
    {
        return ColorSwitch.highScore;
    }

    private void setUpWindow()
    {
        this.applicationWindow.setTitle("Color Switch");
        this.applicationWindow.initStyle(StageStyle.UNDECORATED);
    }

    private void loadStats()
    {
        StatsRecord statsRecord=null;

        try
        {
            FileInputStream file=new FileInputStream(ColorSwitch.statsLocation);
            ObjectInputStream in=new ObjectInputStream(file);
            statsRecord=(StatsRecord)in.readObject();
            in.close();
            file.close();
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        ColorSwitch.gamesPlayed=statsRecord.getGamesPlayed();
        ColorSwitch.averageScore=statsRecord.getAverageScore();
        ColorSwitch.totalStarsCollected=statsRecord.getTotalStarsCollected();
        ColorSwitch.highScore=statsRecord.getHighScore();
        ColorSwitch.savedGames= statsRecord.getSavedGames();
    }
    public void saveStats()
    {
        StatsRecord statsRecord=new StatsRecord(ColorSwitch.gamesPlayed,ColorSwitch.averageScore,ColorSwitch.totalStarsCollected,ColorSwitch.highScore,ColorSwitch.savedGames);

        try
        {
            FileOutputStream file=new FileOutputStream(ColorSwitch.statsLocation);
            ObjectOutputStream out=new ObjectOutputStream(file);
            out.writeObject(statsRecord);
            out.close();
            file.close();
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
            ex.printStackTrace(System.out);
        }
    }

    private void updateStats()
    {
        if(this.currentGame!=null)
        {
            this.updateAverageScore();
            this.updateTotalStarsCollected();
            this.updateHighScore();
        }
    }
    private void updateAverageScore()
    {
        ColorSwitch.averageScore=(ColorSwitch.averageScore*(ColorSwitch.gamesPlayed-1)+this.currentGame.getScore())/ColorSwitch.gamesPlayed;
    }
    private void updateTotalStarsCollected()
    {
        ColorSwitch.totalStarsCollected+=this.currentGame.getStarsCollected();
    }
    private void updateHighScore()
    {
        ColorSwitch.highScore=Math.max(ColorSwitch.highScore,this.currentGame.getScore());
    }
    private void buttonSound(){
        String path = "resources/Sound Effects/button.wav";
        Media media=new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setVolume(1.0);

    }
    private void backgroundSound(){
        String path = "resources/Sound Effects/background.mp3";
        Media media=new Media(new File(path).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.setVolume(0.4);

    }

    public void gameOver()
    {
        VBox gameover = new VBox();
        gameover.setSpacing(10);
        gameover.setAlignment(Pos.CENTER);
        gameover.setPrefWidth(600);
        gameover.setPrefHeight(50);

        Label dummyA= new Label("");
        dummyA.setFont(Font.font("Arial", FontWeight.BOLD,130));
        Label dummyB= new Label("");
        dummyB.setFont(Font.font("Arial", FontWeight.BOLD,10));
        Label Over= new Label("Game Over");
        Over.setTextFill(Color.ALICEBLUE);
        Over.setFont(Font.font("Arial", FontWeight.BOLD,20));

        Label Message= new Label("Do you wish to continue using 5 Stars?");
        Message.setTextFill(Color.ALICEBLUE);
        Message.setFont(Font.font("Arial", FontWeight.BOLD,20));

        Button Continue = new Button("Continue");
        Continue.setStyle("-fx-background-color: #ff4500");
        Continue.setFont(Font.font("Arial", FontWeight.BOLD,30));
        Continue.setMinWidth(160);

        Button Exit = new Button("Exit");
        Exit.setStyle("-fx-background-color: #ff4500");
        Exit.setFont(Font.font("Arial", FontWeight.BOLD,30));
        Exit.setMinWidth(160);

        gameover.getChildren().addAll(dummyA,Message,dummyB, Continue,Exit);
        gameover.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");

        Stage popupStage = new Stage(StageStyle.TRANSPARENT);
        popupStage.initOwner(applicationWindow);
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setScene(new Scene(gameover,Color.TRANSPARENT));
        popupStage.show();
        Continue.setOnAction(event -> {
            this.buttonSound();
            if(currentGame.getStarsCollected()>=5){
                currentGame.reduceStars(5);
                popupStage.hide();
                //currentGame.easeGameSpace();
                this.restartGame();
                //currentGame.start();
            }
            else {
                popupStage.hide();
                VBox vbox = new VBox();
                vbox.setSpacing(10);
                vbox.setAlignment(Pos.CENTER);
                vbox.setPrefWidth(600);
                vbox.setPrefHeight(50);

                Label dummy1= new Label("");
                dummy1.setFont(Font.font("Arial", FontWeight.BOLD,130));
                Label dummy2= new Label("");
                dummy2.setFont(Font.font("Arial", FontWeight.BOLD,10));

                Label InsufficientstarMessage= new Label("Not Sufficient Stars");
                InsufficientstarMessage.setTextFill(Color.ALICEBLUE);
                InsufficientstarMessage.setFont(Font.font("Arial", FontWeight.BOLD,20));

                Button exit = new Button("Exit");
                exit.setStyle("-fx-background-color: #ff4500");
                exit.setFont(Font.font("Arial", FontWeight.BOLD,30));
                exit.setMinWidth(160);
                vbox.getChildren().addAll(dummy1,InsufficientstarMessage,dummy2, exit);
                vbox.setStyle("-fx-background-color: rgba(255, 255, 255, 0.0);");

                Stage popupStage2 = new Stage(StageStyle.TRANSPARENT);
                popupStage2.initOwner(applicationWindow);
                popupStage2.initModality(Modality.APPLICATION_MODAL);
                popupStage2.setScene(new Scene(vbox,Color.TRANSPARENT));
                popupStage2.show();
                exit.setOnAction(insufficientStarsevent -> {
                    this.buttonSound();
                    popupStage2.hide();
                    this.setUpMainMenu();
                });
            }
        });
        Exit.setOnAction(event -> {
            this.buttonSound();
            popupStage.hide();
            this.gameFinished();
        });
    }
    public void restartGame(){
        currentGame.easeGameSpace();
        currentGame.start();
    }

    public void setUpMainMenu()
    {
        VBox menuOptions=new VBox();
        HBox title=new HBox();
        title.setSpacing(10);
        title.setAlignment(Pos.CENTER);
        title.setPrefWidth(25);
        title.setPrefHeight(25);

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        //Creating an image
        Image image = null;
        try {
            image = new Image(new FileInputStream("resources/Images/ColorSwitch.jpeg"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //Setting the image view
        ImageView imageView = new ImageView(image);

        //Setting the position of the image
        imageView.setX(50);
        imageView.setY(25);

        //setting the fit height and width of the image view
        imageView.setFitHeight(100);
        imageView.setFitWidth(400);

        //Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);


        Button newGameButton=new Button("NEW GAME");
        //vghvhg
        newGameButton.setStyle("-fx-font-weight: bold");
        newGameButton.setStyle("-fx-background-color: #AB4642");

        newGameButton.setOnAction(newGameEvent ->{
            this.buttonSound();
            this.startNewGame();
        });

        Button loadGameButton=new Button("LOAD GAME");
        loadGameButton.setStyle("-fx-background-color: #00bfff");
        loadGameButton.setOnAction(loadGameEvent -> {
            this.buttonSound();
            this.setUpLoadMenu();
        });

        Button statsButton=new Button("STATS");
        statsButton.setStyle("-fx-background-color: #e9967a");
        statsButton.setOnAction(statsevent -> {
            this.buttonSound();
            setUpStatsMenu();
        });

        Button helpButton=new Button("HELP");
        helpButton.setStyle("-fx-background-color: #00fa9a");
        helpButton.setOnAction(helpevent -> {
            this.buttonSound();
            setUpHelpMenu();
        });

        Button creditsButton=new Button("CREDITS");
        creditsButton.setStyle("-fx-background-color: #F7CA88");
        creditsButton.setOnAction(creditsevent -> {
            this.buttonSound();
            this.setUpCreditsMenu();
        });

        Button exitButton=new Button("EXIT");
        exitButton.setStyle("-fx-background-color: #f08080");
        exitButton.setOnAction(exitEvent -> {
            this.buttonSound();
            this.closeProgram();
        });

        Button[] menuButtons={newGameButton,loadGameButton,statsButton,helpButton,creditsButton,exitButton};

        for(Button button : menuButtons)
        {
            button.setMinWidth(menuOptions.getPrefWidth());
            button.setMinHeight(menuOptions.getPrefHeight());
        }
        title.getChildren().add(imageView);
        menuOptions.getChildren().addAll(title,newGameButton,loadGameButton,statsButton,helpButton,creditsButton,exitButton);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);

        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());


        this.applicationWindow.setScene(mainMenu);
    }
    private void setUpLoadMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        Label Tittle= new Label("LOAD GAME");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy1= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy2= new Label(" ");

        Button File1=new Button("...");
        File1.setStyle("-fx-background-color: #AB4642");

        Button File2=new Button("...");
        File2.setStyle("-fx-background-color: #00bfff");

        Button File3 =new Button("...");
        File3.setStyle("-fx-background-color: #e9967a");

        Button File4=new Button("...");
        File4.setStyle("-fx-background-color: #00fa9a");

        Button File5=new Button("...");
        File5.setStyle("-fx-background-color: #F7CA88");

        Button Back= new Button("BACK");
        Back.setStyle("-fx-background-color: #ff4500");
        Back.setOnAction(event -> {
            this.buttonSound();
            this.setUpMainMenu();
        });

        Button[] Files={File1,File2,File3,File4,File5};
        int fileID=0;
        for(Button File : Files)
        {
            if(ColorSwitch.savedGames.size()>fileID)
            {
                int finalFileID=fileID;
                File.setText("Saved State "+fileID);
                File.setOnAction(newGameEvent -> {
                    this.buttonSound();
                    this.loadGame(finalFileID);
                });
            }
            ++fileID;
        }

         Button[] menuButtons={File1,File2,File3,File4,File5,Back};
        for(Button button : menuButtons)
        {
            button.setMinWidth(menuOptions.getPrefWidth());
            button.setMinHeight(menuOptions.getPrefHeight());
        }

        menuOptions.getChildren().addAll(Tittle,dummy1,File1,File2,File3,File4,File5,Back);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());

        this.applicationWindow.setScene(mainMenu);
    }
    private void setUpCreditsMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        Label Tittle= new Label("CREDITS");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy1= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy2= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label label1=new Label("The App is developed by Abhimanyu And Shubham");
        Label label2=new Label("To get full information about the project");
        Label label3=new Label("Visit the following link");
        Hyperlink repositoryLink=new Hyperlink("https://github.com/0deadLock0/Color-Switch");
        repositoryLink.setOnAction(e ->	getHostServices().showDocument(repositoryLink.getText()));
        Label label4 =new Label(" ");
        Label label5 =new Label(" ");

        Button Back= new Button("BACK");
        Back.setStyle("-fx-background-color: #ff4500");
        Back.setOnAction(event -> {
            this.buttonSound();
            this.setUpMainMenu();
        });

        Back.setMinWidth(menuOptions.getPrefWidth());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,label1,label2,label3,repositoryLink,label4,label5,Back);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());


        this.applicationWindow.setScene(mainMenu);
    }
    private void setUpHelpMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        Label Tittle= new Label("HELP");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy1= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy2= new Label(" ");
        Label label1=new Label("-> Collect stars while avoiding obstacles");
        Label label2=new Label("-> Use following buttons to interact inside the game");
        Label label3=new Label("1) Move up using Space-bar");
        Label label4=new Label("2) Use Z to pause the game");
        Label label5 =new Label(" ");
        Label label6 =new Label(" ");

        Button Back= new Button("BACK");
        Back.setStyle("-fx-background-color: #ff4500");
        Back.setOnAction(event -> {
            this.buttonSound();
            this.setUpMainMenu();
        });

        Back.setMinWidth(menuOptions.getPrefWidth());
//      Back.setMinHeight(menuOptions.getPrefHeight());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,label1,label2,label3,label4,label5,label6,Back);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());


        this.applicationWindow.setScene(mainMenu);
    }
    private void setUpStatsMenu()
    {
        VBox menuOptions=new VBox();

        menuOptions.setSpacing(10);
        menuOptions.setAlignment(Pos.CENTER);
        menuOptions.setPrefWidth(100);
        menuOptions.setPrefHeight(50);

        Label Tittle= new Label("STATS");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy1= new Label(" ");
        Tittle.getStyleClass().add("heading");
        Tittle.setLayoutY(10);
        Label dummy2= new Label(" ");
        Label gamesCountLabel=new Label("Games Played: "+ColorSwitch.gamesPlayed);
        Label averageScoreLabel=new Label("Average Score: "+((ColorSwitch.gamesPlayed==0)?"NA":ColorSwitch.averageScore));
        Label starsCollectedLabel=new Label("Total Stars Collected: "+((ColorSwitch.gamesPlayed==0)?"NA":ColorSwitch.totalStarsCollected));
        Label highScoreLabel=new Label("High Score: "+((ColorSwitch.gamesPlayed==0)?"NA":ColorSwitch.highScore));
        Label label5=new Label("");
        Label label6=new Label("");

        Button Back= new Button("BACK");
        Back.setStyle("-fx-background-color: #ff4500");
        Back.setOnAction(event -> {
            this.buttonSound();
            this.setUpMainMenu();
        });


        Back.setMinWidth(menuOptions.getPrefWidth());
        menuOptions.getChildren().addAll(Tittle,dummy1,dummy2,gamesCountLabel,averageScoreLabel,starsCollectedLabel,highScoreLabel,label5,label6,Back);
        Scene mainMenu=new Scene(menuOptions,Settings.DesiredSceneWidth,Settings.DesiredSceneHeight);
        //changes1
        mainMenu.getStylesheets().add(ColorSwitch.class.getResource("styles.css").toExternalForm());


        this.applicationWindow.setScene(mainMenu);
    }

    public String saveGame()
    {
        int fileId=ColorSwitch.savedGames.size();
        if(fileId==ColorSwitch.maxRecords)
            fileId=(new Random()).nextInt(ColorSwitch.maxRecords);
        String fileName=ColorSwitch.recordsDirectory +"\\Record"+fileId+".ser";
        if(fileId==ColorSwitch.savedGames.size())
            ColorSwitch.savedGames.add(fileName);
        GameRecord gameRecord=new GameRecord(this.currentGame.getScore(),("Saved State "+fileId),this.currentGame);

        try
        {
            FileOutputStream file = new FileOutputStream(fileName);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(gameRecord);
            out.close();
            file.close();
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
            ex.printStackTrace(System.out);
        }
        return gameRecord.getName();
    }
    private void loadGame(int option)
    {
        String fileName=ColorSwitch.savedGames.get(option);
        GameRecord gameRecord=null;

        try
        {
            FileInputStream file=new FileInputStream(fileName);
            ObjectInputStream in=new ObjectInputStream(file);
            gameRecord=(GameRecord)in.readObject();
            in.close();
            file.close();
        }
        catch(IOException ex)
        {
            System.out.println("IOException is caught");
        }
        catch(ClassNotFoundException ex)
        {
            System.out.println("ClassNotFoundException is caught");
        }

        this.currentGame=gameRecord.getGameSpace();
        this.currentGame.construct(this, this.applicationWindow, Settings.DesiredSceneWidth, Settings.DesiredSceneHeight);

        this.launchGame();
    }

    private void startNewGame()
    {
        ++ColorSwitch.gamesPlayed;
        this.currentGame=new GameSpace(this, this.applicationWindow, Settings.DesiredSceneWidth, Settings.DesiredSceneHeight);
        this.launchGame();
    }

    private void launchGame()
    {
        if(currentGame==null)
        {
            System.out.println("No game currently active");
            return;
        }
        this.currentGame.start();
    }

    void gameFinished()
    {
        this.updateStats();
        this.currentGame=null;
        this.setUpMainMenu();
    }

    void closeProgram()
    {
        this.saveStats();
        this.applicationWindow.close();
    }

    @Override
    public void start(Stage primaryStage)
    {
        this.backgroundSound();
        this.applicationWindow=primaryStage;

        this.loadStats();

        this.setUpWindow();

        this.setUpMainMenu();

        this.applicationWindow.hide();
        this.applicationWindow.show();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}