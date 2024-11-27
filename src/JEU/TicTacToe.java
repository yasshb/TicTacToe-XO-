package JEU;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {
    int boardWidth = 600;  // Largeur du plateau de jeu
    int boardHeight = 650; // Hauteur du plateau de jeu, incluant un panneau de texte de 50px en haut

    JFrame frame = new JFrame("Tic-Tac-Toe"); // Création de la fenêtre de jeu
    JLabel textLabel = new JLabel();          // Label pour afficher le texte (ex : tour de jeu ou gagnant)
    JPanel textPanel = new JPanel();          // Panneau pour contenir le label de texte
    JPanel boardPanel = new JPanel();         // Panneau pour contenir les boutons du jeu

    JButton[][] board = new JButton[3][3];    // Tableau 2D de boutons représentant les cases du jeu
    String playerX = "X";                     // Représentation du joueur X
    String playerO = "O";                     // Représentation du joueur O
    String currentPlayer = playerX;           // Joueur actuel, initialisé à X

    boolean gameOver = false;                 // Indicateur de fin de jeu
    int turns = 0;                            // Compteur de tours de jeu

    // Constructeur
    public TicTacToe() {
        // Configuration de la fenêtre principale
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // Ouvre la fenêtre au centre de l'écran
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Permet de fermer la fenêtre
        frame.setLayout(new BorderLayout());

        // Configuration du label de texte
        textLabel.setBackground(Color.darkGray);
        textLabel.setForeground(Color.white);
        textLabel.setFont(new Font("Arial", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        // Ajout du label de texte au panneau de texte
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // Configuration du panneau du plateau de jeu
        boardPanel.setLayout(new GridLayout(3, 3));
        boardPanel.setBackground(Color.darkGray);
        frame.add(boardPanel);

        // Initialisation des boutons du plateau de jeu
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.black);
                tile.setForeground(Color.white);
                tile.setFont(new Font("Arial", Font.BOLD, 120));
                tile.setFocusable(false);

                // Ajout d'un ActionListener à chaque bouton pour gérer les clics
                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (gameOver) return; // Si le jeu est terminé, ne rien faire
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText() == "") { // Si la case est vide
                            tile.setText(currentPlayer); // Définir le texte de la case comme le joueur actuel
                            turns++;
                            checkWinner(); // Vérifier si il y a un gagnant
                            if (!gameOver) { // Si le jeu n'est pas terminé
                                currentPlayer = currentPlayer == playerX ? playerO : playerX; // Changer de joueur
                                textLabel.setText(currentPlayer + "'s turn."); // Afficher le joueur actuel
                            }
                        }
                    }
                });
            }
        }
    }

    // Méthode pour vérifier s'il y a un gagnant
    void checkWinner() {
        // Vérification horizontale
        for (int r = 0; r < 3; r++) {
            if (board[r][0].getText() == "") continue;
            if (board[r][0].getText() == board[r][1].getText() &&
                    board[r][1].getText() == board[r][2].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[r][i]); // Marquer les cases gagnantes
                }
                gameOver = true;
                return;
            }
        }

        // Vérification verticale
        for (int c = 0; c < 3; c++) {
            if (board[0][c].getText() == "") continue;
            if (board[0][c].getText() == board[1][c].getText() &&
                    board[1][c].getText() == board[2][c].getText()) {
                for (int i = 0; i < 3; i++) {
                    setWinner(board[i][c]); // Marquer les cases gagnantes
                }
                gameOver = true;
                return;
            }
        }

        // Vérification diagonale
        if (board[0][0].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][2].getText() &&
                board[0][0].getText() != "") {
            for (int i = 0; i < 3; i++) {
                setWinner(board[i][i]); // Marquer les cases gagnantes
            }
            gameOver = true;
            return;
        }

        // Vérification anti-diagonale
        if (board[0][2].getText() == board[1][1].getText() &&
                board[1][1].getText() == board[2][0].getText() &&
                board[0][2].getText() != "") {
            setWinner(board[0][2]);
            setWinner(board[1][1]);
            setWinner(board[2][0]);
            gameOver = true;
            return;
        }

        // Si toutes les cases sont remplies sans gagnant, déclarer un match nul
        if (turns == 9) {
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    setTie(board[r][c]); // Marquer les cases comme match nul
                }
            }
            gameOver = true;
        }
    }

    // Méthode pour marquer un gagnant
    void setWinner(JButton tile) {
        tile.setForeground(Color.green); // Changer la couleur du texte des cases gagnantes
        tile.setBackground(Color.gray);  // Changer la couleur de fond des cases gagnantes
        textLabel.setText(currentPlayer + " is the winner!"); // Afficher le message de victoire
    }

    // Méthode pour marquer un match nul
    void setTie(JButton tile) {
        tile.setForeground(Color.red); // Changer la couleur du texte des cases pour le match nul
        tile.setBackground(Color.black);   // Changer la couleur de fond des cases pour le match nul
        textLabel.setText("Tie!");        // Afficher le message de match nul
    }
}
