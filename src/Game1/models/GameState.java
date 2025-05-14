package Game1.models;



import java.io.Serial;
import java.io.Serializable;



public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Board board;
    private String username;

    public GameState(Board board, String username) {
        this.board = board;
        this.username = username;
    }

    public Board getBoard() {
        return board;
    }

    public String getUsername() {
        return username;
    }
}