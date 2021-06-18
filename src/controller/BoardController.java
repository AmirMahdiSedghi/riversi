package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Color;
import model.Piece;
import model.Player;
import model.Status;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class BoardController implements Initializable {
    @FXML
    private Label score1;
    @FXML
    private Label score2;
    @FXML
    private Label turn1;
    @FXML
    private Label turn2;
    @FXML
    private VBox board;
    @FXML
    private Label playerName1;
    @FXML
    private Label playerName2;
    public static Player player1= new Player();
    public static Player player2= new Player();
    public static ArrayList<Player>players = new ArrayList<>();

    private boolean greenTurn = true;

    /**
     * this global array is meant to store all the pieces of the field
     *
     * @author rezaBh
     */
    private final Piece[][] pieces = new Piece[8][8];


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initPieces();
        selectPieceForPlay();
    }


    /**
     * makes pieces for player selectable.
     * player does her own move
     * author AmirMahdi
     */
    public void setSelectables() {
        for (int i = 0; i <= 7; i++) {
            for (int j = 0; j <= 7; j++) {
                if (greenTurn) {
                    checkingTheRightRowForGreen(pieces[i][j],i,j);
                    checkingTheLeftRowForGreen(pieces[i][j], i, j);
                    checkingTheUpColumnForGreen(pieces[i][j],i,j);
                    checkingTheDownColumnForGreen(pieces[i][j],i,j);
                    checkingTheUpRightDiameterForGreen(pieces[i][j],i,j);
                    checkingTheUpLeftDiameterForGreen(pieces[i][j],i,j);
                    checkingTheDownRightDiameterForGreen(pieces[i][j],i,j);
                    checkingTheDownLeftDiameterForGreen(pieces[i][j],i,j);
                }else {
                    checkingTheRightRowForBlack(pieces[i][j],i,j);
                    checkingTheLeftRowForBlack(pieces[i][j],i,j);
                    checkingTheUpColumnForBlack(pieces[i][j],i,j);
                    checkingTheDownColumnForBlack(pieces[i][j],i,j);
                    checkingTheUpRightDiameterForBlack(pieces[i][j],i,j);
                    checkingTheUpLeftDiameterForBlack(pieces[i][j],i,j);
                    checkingTheDownRightDiameterForBlack(pieces[i][j],i,j);
                    checkingTheDownLeftDiameterForBlack(pieces[i][j],i,j);

                }
            }
        }
    }

    /**
     * When the player has taken her turn,
     * the continuation of the game will be performed by calling the functions in this method
     * @author AmirMahdi
     */
    public void selectPieceForPlay(){
        setSelectables();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                int finalI = i;
                int finalJ = j;
                pieces[i][j].setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        if (greenTurn && pieces[finalI][finalJ].getStatus().equals(Status.selectable)){
                            pieces[finalI][finalJ].setPieceGreen();
                            rotatePiecesInRightRow(pieces[finalI][finalJ], finalI, finalJ);
                            rotatePiecesInLeftRow(pieces[finalI][finalJ], finalI, finalJ);
                            rotatePiecesInDownColumn(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInUpColumn(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInUpMainDiameter(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInDownMainDiameter(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInDownSubDiameter(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInUpSubDiameter(pieces[finalI][finalJ],finalI,finalJ);
                            changeTurn();
                            clearSelectable();
                            setSelectables();
                        }else if (!greenTurn && pieces[finalI][finalJ].getStatus().equals(Status.selectable)){
                            pieces[finalI][finalJ].setPieceBlack();
                            rotatePiecesInRightRow(pieces[finalI][finalJ], finalI, finalJ);
                            rotatePiecesInLeftRow(pieces[finalI][finalJ], finalI, finalJ);
                            rotatePiecesInDownColumn(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInUpColumn(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInUpMainDiameter(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInDownMainDiameter(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInDownSubDiameter(pieces[finalI][finalJ],finalI,finalJ);
                            rotatePiecesInUpSubDiameter(pieces[finalI][finalJ],finalI,finalJ);
                            changeTurn();
                            clearSelectable();
                            setSelectables();
                        }
                    }
                });
            }
        }
    }

    public void rotatePiecesInRightRow(Piece piece, int i, int j){
        if (j==7){
            return;
        }
        Color newPieceColor = piece.getPieceColor();
        if (pieces[i][j+1].getPieceColor() != null && !pieces[i][j+1].getPieceColor().equals(newPieceColor)){
            int startRotating=j+1;
            int endRotating =-1;
            boolean find=false;
            for (int k = j+1; k <= 7; k++) {
                if (pieces[i][k].getPieceColor() != null && pieces[i][k].getPieceColor().equals(newPieceColor)){
                    endRotating=k-1;
                    find = true;
                    break;
                }else if (pieces[i][k].getPieceColor() == null){
                    return;
                }
            }
            if (find) {
                for (int k = startRotating; k <= endRotating; k++) {
                    pieces[i][k].setPieceGivenColor(newPieceColor);
                }
            }
        }
    }
    public void rotatePiecesInLeftRow(Piece piece, int i, int j){
        if (j==0){
            return;
        }
        Color newPieceColor = piece.getPieceColor();
        if (pieces[i][j-1].getPieceColor() != null && !pieces[i][j-1].getPieceColor().equals(newPieceColor)){
            int startRotating=j-1;
            int endRotating =-1;
            boolean find=false;
            for (int k = j-1; k >= 0; k--) {
                if (pieces[i][k].getPieceColor() != null && pieces[i][k].getPieceColor().equals(newPieceColor)){
                    endRotating=k+1;
                    find = true;
                    break;
                }else if (pieces[i][k].getPieceColor() == null){
                    return;
                }
            }
            if (find) {
                for (int k = startRotating; k >= endRotating; k--) {
                    pieces[i][k].setPieceGivenColor(newPieceColor);
                }
            }
        }
    }
    public void rotatePiecesInDownColumn(Piece piece, int i, int j){
        if (i==7){
            return;
        }
        Color newPieceColor = piece.getPieceColor();
        if (pieces[i+1][j].getPieceColor() != null && !pieces[i+1][j].getPieceColor().equals(newPieceColor)){
            int startRotating = i+1;
            int endRotating =-1;
            boolean find = false;
            for (int k = i+1; k <=7; k++) {
                if (pieces[k][j].getPieceColor() != null && pieces[k][j].getPieceColor().equals(newPieceColor)){
                    endRotating=k-1;
                    find = true;
                    break;
                }else if (pieces[k][j].getPieceColor() == null){
                    return;
                }
            }
            if (find) {
                for (int k = startRotating; k <= endRotating; k++) {
                    pieces[k][j].setPieceGivenColor(newPieceColor);
                }
            }
        }
    }
    public void rotatePiecesInUpColumn(Piece piece, int i, int j){
        if (i==0){
            return;
        }
        Color newPieceColor = piece.getPieceColor();
        if (pieces[i-1][j].getPieceColor() != null && !pieces[i-1][j].getPieceColor().equals(newPieceColor)){
            int startRotating = i-1;
            int endRotating =-1;
            boolean find = false;
            for (int k = i-1; k>=0; k--) {
                if (pieces[k][j].getPieceColor() != null && pieces[k][j].getPieceColor().equals(newPieceColor)){
                    endRotating=k+1;
                    find = true;
                    break;
                }else if (pieces[k][j].getPieceColor() == null){
                    return;
                }
            }
            if (find) {
                for (int k = startRotating; k >= endRotating; k--) {
                    pieces[k][j].setPieceGivenColor(newPieceColor);
                }
            }
        }
    }
    public void rotatePiecesInDownMainDiameter(Piece piece, int i, int j){
        if (i==7 || j==7){
            return;
        }
        Color newPieceColor = piece.getPieceColor();
        if (pieces[i+1][j+1].getPieceColor() != null && !pieces[i+1][j+1].getPieceColor().equals(newPieceColor)){
            int startRotatingRow = i+1;
            int startRotatingColumn = j+1;
            int endRotatingRow =-1;
            int endRotatingColimn =-1;
            boolean find = false;
            for (int k = i+1, p=j+1; k <=7 && p<=7; k++ , p++) {
                if (pieces[k][p].getPieceColor() != null && pieces[k][p].getPieceColor().equals(newPieceColor)){
                    endRotatingRow=k-1;
                    endRotatingColimn=p-1;
                    find = true;
                    break;
                }else if (pieces[k][p].getPieceColor() == null){
                    return;
                }
            }
            if (find) {
                for (int k = startRotatingRow , p = startRotatingColumn; k <= endRotatingRow && p<= endRotatingColimn; k++ , p++) {
                    pieces[k][p].setPieceGivenColor(newPieceColor);
                }
            }
        }
    }
    public void rotatePiecesInUpMainDiameter(Piece piece, int i, int j){
        if (i==0 || j==0){
            return;
        }
        Color newPieceColor = piece.getPieceColor();
        if (pieces[i-1][j-1].getPieceColor() != null && !pieces[i-1][j-1].getPieceColor().equals(newPieceColor)){
            int startRotatingRow = i-1;
            int startRotatingColumn = j-1;
            int endRotatingRow =-1;
            int endRotatingColimn =-1;
            boolean find = false;
            for (int k = i-1, p=j-1; k >=0 && p>=0; k-- , p--) {
                if (pieces[k][p].getPieceColor() != null && pieces[k][p].getPieceColor().equals(newPieceColor)){
                    endRotatingRow=k+1;
                    endRotatingColimn=p+1;
                    find = true;
                    break;
                }else if (pieces[k][p].getPieceColor() == null){
                    return;
                }
            }
            if (find) {
                for (int k = startRotatingRow , p = startRotatingColumn; k >= endRotatingRow && p>= endRotatingColimn; k-- , p--) {
                    pieces[k][p].setPieceGivenColor(newPieceColor);
                }
            }
        }
    }
    public void rotatePiecesInDownSubDiameter(Piece piece, int i, int j){
        if (i==7 || j==0){
            return;
        }
        Color newPieceColor = piece.getPieceColor();
        if (pieces[i+1][j-1].getPieceColor() != null && !pieces[i+1][j-1].getPieceColor().equals(newPieceColor)){
            int startRotatingRow = i+1;
            int startRotatingColumn = j-1;
            int endRotatingRow =-1;
            int endRotatingColumn =-1;
            boolean find = false;
            for (int k = i+1, p=j-1; k <= 7 && p >= 0; k++ , p--) {
                if (pieces[k][p].getPieceColor() != null && pieces[k][p].getPieceColor().equals(newPieceColor)){
                    endRotatingRow=k-1;
                    endRotatingColumn=p+1;
                    find = true;
                    break;
                }else if (pieces[k][p].getPieceColor() == null){
                    return;
                }
            }
            if (find) {
                for (int k = startRotatingRow , p = startRotatingColumn; k <= endRotatingRow && p>= endRotatingColumn; k++ , p--) {
                    pieces[k][p].setPieceGivenColor(newPieceColor);
                }
            }
        }
    }
    public void rotatePiecesInUpSubDiameter(Piece piece, int i, int j){
        if (i==0 || j==7){
            return;
        }
        Color newPieceColor = piece.getPieceColor();
        if (pieces[i-1][j+1].getPieceColor() != null && !pieces[i-1][j+1].getPieceColor().equals(newPieceColor)){
            int startRotatingRow = i-1;
            int startRotatingColumn = j+1;
            int endRotatingRow =-1;
            int endRotatingColumn =-1;
            boolean find = false;
            for (int k = i-1, p=j+1; k >= 0 && p <= 7; k-- , p++) {
                if (pieces[k][p].getPieceColor() != null && pieces[k][p].getPieceColor().equals(newPieceColor)){
                    endRotatingRow=k+1;
                    endRotatingColumn=p-1;
                    find = true;
                    break;
                }else if (pieces[k][p].getPieceColor() == null){
                    return;
                }
            }
            if (find) {
                for (int k = startRotatingRow , p = startRotatingColumn; k >= endRotatingRow && p <= endRotatingColumn; k-- , p++) {
                    pieces[k][p].setPieceGivenColor(newPieceColor);
                }
            }
        }
    }


    /**
     * After each move, each player takes turns
     * @author AmirMahdi
     */
    public void changeTurn(){
        if (greenTurn){
            greenTurn=false;
        }
        else{
            greenTurn = true;
        }
    }

    /**
     * After selecting each of the selectable pieces,
     * it is the opponent's turn.
     * For this reason, the pieces that can be selected for the previous player must be removed
     * @author AmirMahdi
     */
    public void clearSelectable(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (pieces[i][j].getPieceColor() == null){
                    pieces[i][j].setPieceUnselectable();
                }
            }
        }
    }


    /**
     * it will be called only at the beginning of the game.
     * and creates the pieces for start playing the game.
     * only 4 pieces is selected. 2 green and 2 white at the center of board.
     * so the other pieces are unselectable
     *
     * @author AmirMahdi
     */
    private void initPieces() {
        for (int i = 0; i < 8; i++) {
            HBox hBox = new HBox();
            hBox.setAlignment(Pos.CENTER);
            hBox.setSpacing(1);
            for (int j = 0; j < 8; j++) {
                if (i == 3 && j == 3 || i == 4 && j == 4) {
                    pieces[i][j] = new Piece(Status.selected, Color.green);
                    hBox.getChildren().add(pieces[i][j]);
                } else if (i == 3 && j == 4 || i == 4 && j == 3) {
                    pieces[i][j] = new Piece(Status.selected, Color.black);
                    hBox.getChildren().add(pieces[i][j]);
                } else {
                    pieces[i][j] = new Piece(Status.unselectable);
                    hBox.getChildren().add(pieces[i][j]);
                }
            }
            board.getChildren().add(hBox);
        }
    }

    // Functions to identify selectable pieces for green player
    private void checkingTheRightRowForGreen(Piece piece, int i, int j) {
        if (j == 7) {
            return;
        }
        if (piece.getPieceColor() == Color.green && pieces[i][j + 1].getPieceColor() == Color.black) {
            System.out.println("oh my god. next piece of it is black. so are there any white or unselected piece after it?");
            for (int k = j + 1; k < 8; k++) {
                if (pieces[i][k].getPieceColor() != null) {
                    if (pieces[i][k].getPieceColor() != Color.black) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + i + " " + k + " is " + pieces[i][k].getPieceColor());
                } else {
                    System.out.println("the piece " + i + " " + k + " has no color. so it must be selectable. finish right for this green piece ");
                    pieces[i][k].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheLeftRowForGreen(Piece piece, int i, int j) {
        if (j == 0) {
            return;
        }
        if (piece.getPieceColor() == Color.green && pieces[i][j - 1].getPieceColor() == Color.black) {
            System.out.println("oh my god. previous piece of it is white. so are there any white or unselected piece after it?");
            for (int k = j - 1; k >= 0; k--) {
                if (pieces[i][k].getPieceColor() != null) {
                    if (pieces[i][k].getPieceColor() != Color.black) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + i + " " + k + " is " + pieces[i][k].getPieceColor());
                } else {
                    System.out.println("the piece " + i + " " + k + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[i][k].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheUpColumnForGreen(Piece piece, int i, int j) {
        if (i == 0) {
            return;
        }
        if (piece.getPieceColor() == Color.green && pieces[i-1][j].getPieceColor() == Color.black) {
            System.out.println("oh my god. previous piece of it is white. so are there any white or unselected piece after it?");
            for (int k = i - 1; k >= 0; k--) {
                if (pieces[k][j].getPieceColor() != null) {
                    if (pieces[k][j].getPieceColor() != Color.black) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + j + " is " + pieces[k][j].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + j + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][j].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheDownColumnForGreen(Piece piece, int i, int j) {
        if (i == 7) {
            return;
        }
        if (piece.getPieceColor() == Color.green && pieces[i+1][j].getPieceColor() == Color.black) {
            System.out.println("oh my god. previous piece of it is white. so are there any white or unselected piece after it?");
            for (int k = i + 1; k <= 7; k++) {
                if (pieces[k][j].getPieceColor() != null) {
                    if (pieces[k][j].getPieceColor() != Color.black) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + j + " is " + pieces[k][j].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + j + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][j].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }

    private void checkingTheUpRightDiameterForGreen(Piece piece, int i, int j) {
        if (i == 0 || j == 7) {
            return;
        }
        if (piece.getPieceColor() == Color.green && pieces[i-1][j + 1].getPieceColor() == Color.black) {
            System.out.println("oh my god. up right diameter piece of it is white. so are there any white or unselected piece after it?");
            for (int k=i-1, h=j+1; k>=0 && h<=7; k-- , h++) {
                if (pieces[k][h].getPieceColor() != null) {
                    if (pieces[k][h].getPieceColor() != Color.black) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + h + " is " + pieces[k][h].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + h + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][h].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheUpLeftDiameterForGreen(Piece piece, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (piece.getPieceColor() == Color.green && pieces[i-1][j - 1].getPieceColor() == Color.black) {
            System.out.println("oh my god. up right diameter piece of it is white. so are there any white or unselected piece after it?");
            for (int k=i-1, h=j-1; k>=0 && h>=0; k-- , h--) {
                if (pieces[k][h].getPieceColor() != null) {
                    if (pieces[k][h].getPieceColor() != Color.black) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + h + " is " + pieces[k][h].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + h + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][h].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheDownRightDiameterForGreen(Piece piece, int i, int j) {
        if (i == 7 || j == 7) {
            return;
        }
        if (piece.getPieceColor() == Color.green && pieces[i+1][j + 1].getPieceColor() == Color.black) {
            System.out.println("oh my god. up right diameter piece of it is white. so are there any white or unselected piece after it?");
            for (int k=i+1, h=j+1; k<=7 && h<=7; k++ , h++) {
                if (pieces[k][h].getPieceColor() != null) {
                    if (pieces[k][h].getPieceColor() != Color.black) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + h + " is " + pieces[k][h].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + h + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][h].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheDownLeftDiameterForGreen(Piece piece, int i, int j) {
        if (i == 7 || j == 0) {
            return;
        }
        if (piece.getPieceColor() == Color.green && pieces[i+1][j - 1].getPieceColor() == Color.black) {
            System.out.println("oh my god. up right diameter piece of it is white. so are there any white or unselected piece after it?");
            for (int k=i+1, h=j-1; k<=7 && h>=0; k++ , h--) {
                if (pieces[k][h].getPieceColor() != null) {
                    if (pieces[k][h].getPieceColor() != Color.black) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + h + " is " + pieces[k][h].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + h + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][h].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }

    // Functions to identify selectable pieces for black player
    private void checkingTheRightRowForBlack(Piece piece, int i, int j) {
        if (j == 7) {
            return;
        }
        if (piece.getPieceColor() == Color.black && pieces[i][j + 1].getPieceColor() == Color.green) {
            System.out.println("oh my god. next piece of it is black. so are there any white or unselected piece after it?");
            for (int k = j + 1; k < 8; k++) {
                if (pieces[i][k].getPieceColor() != null) {
                    if (pieces[i][k].getPieceColor() != Color.green) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + i + " " + k + " is " + pieces[i][k].getPieceColor());
                } else {
                    System.out.println("the piece " + i + " " + k + " has no color. so it must be selectable. finish right for this green piece ");
                    pieces[i][k].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheLeftRowForBlack(Piece piece, int i, int j) {
        if (j == 0) {
            return;
        }
        if (piece.getPieceColor() == Color.black && pieces[i][j - 1].getPieceColor() == Color.green) {
            System.out.println("oh my god. previous piece of it is white. so are there any white or unselected piece after it?");
            for (int k = j - 1; k >= 0; k--) {
                if (pieces[i][k].getPieceColor() != null) {
                    if (pieces[i][k].getPieceColor() != Color.green) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + i + " " + k + " is " + pieces[i][k].getPieceColor());
                } else {
                    System.out.println("the piece " + i + " " + k + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[i][k].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheUpColumnForBlack(Piece piece, int i, int j) {
        if (i == 0) {
            return;
        }
        if (piece.getPieceColor() == Color.black && pieces[i-1][j].getPieceColor() == Color.green) {
            System.out.println("oh my god. previous piece of it is white. so are there any white or unselected piece after it?");
            for (int k = i - 1; k >= 0; k--) {
                if (pieces[k][j].getPieceColor() != null) {
                    if (pieces[k][j].getPieceColor() != Color.green) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + j + " is " + pieces[k][j].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + j + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][j].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheDownColumnForBlack(Piece piece, int i, int j) {
        if (i == 7) {
            return;
        }
        if (piece.getPieceColor() == Color.black && pieces[i+1][j].getPieceColor() == Color.green) {
            System.out.println("oh my god. previous piece of it is white. so are there any white or unselected piece after it?");
            for (int k = i + 1; k <= 7; k++) {
                if (pieces[k][j].getPieceColor() != null) {
                    if (pieces[k][j].getPieceColor() != Color.green) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + j + " is " + pieces[k][j].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + j + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][j].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }

    private void checkingTheUpRightDiameterForBlack(Piece piece, int i, int j) {
        if (i == 0 || j == 7) {
            return;
        }
        if (piece.getPieceColor() == Color.black && pieces[i-1][j + 1].getPieceColor() == Color.green) {
            System.out.println("oh my god. up right diameter piece of it is white. so are there any white or unselected piece after it?");
            for (int k=i-1, h=j+1; k>=0 && h<=7; k-- , h++) {
                if (pieces[k][h].getPieceColor() != null) {
                    if (pieces[k][h].getPieceColor() != Color.green) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + h + " is " + pieces[k][h].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + h + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][h].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheUpLeftDiameterForBlack(Piece piece, int i, int j) {
        if (i == 0 || j == 0) {
            return;
        }
        if (piece.getPieceColor() == Color.black && pieces[i-1][j - 1].getPieceColor() == Color.green) {
            System.out.println("oh my god. up right diameter piece of it is white. so are there any white or unselected piece after it?");
            for (int k=i-1, h=j-1; k>=0 && h>=0; k-- , h--) {
                if (pieces[k][h].getPieceColor() != null) {
                    if (pieces[k][h].getPieceColor() != Color.green) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + h + " is " + pieces[k][h].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + h + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][h].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheDownRightDiameterForBlack(Piece piece, int i, int j) {
        if (i == 7 || j == 7) {
            return;
        }
        if (piece.getPieceColor() == Color.black && pieces[i+1][j + 1].getPieceColor() == Color.green) {
            System.out.println("oh my god. up right diameter piece of it is white. so are there any white or unselected piece after it?");
            for (int k=i+1, h=j+1; k<=7 && h<=7; k++ , h++) {
                if (pieces[k][h].getPieceColor() != null) {
                    if (pieces[k][h].getPieceColor() != Color.green) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + h + " is " + pieces[k][h].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + h + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][h].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }
    private void checkingTheDownLeftDiameterForBlack(Piece piece, int i, int j) {
        if (i == 7 || j == 0) {
            return;
        }
        if (piece.getPieceColor() == Color.black && pieces[i+1][j - 1].getPieceColor() == Color.green) {
            System.out.println("oh my god. up right diameter piece of it is white. so are there any white or unselected piece after it?");
            for (int k=i+1, h=j-1; k<=7 && h>=0; k++ , h--) {
                if (pieces[k][h].getPieceColor() != null) {
                    if (pieces[k][h].getPieceColor() != Color.green) {
                        System.out.println("unfortunately there is no unselected piece");
                        return;
                    }
                    System.out.println("the piece " + k + " " + h + " is " + pieces[k][h].getPieceColor());
                } else {
                    System.out.println("the piece " + k + " " + h + " has no color. so it must be selectable. finish left for this green piece ");
                    pieces[k][h].setPieceSelectable();
                    return;
                }
            }
            System.out.println("nothing");
        }
    }


}