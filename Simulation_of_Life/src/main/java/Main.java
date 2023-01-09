import gui.App;
import javafx.application.Application;

/* Zwierzęta dostają maksymalną ilość energi równą podwójnej startowej energii
 * Nie zostało zaimplementowane hellPortal i wyświetlanie zwierząt z najpopularniejszym genomem
 *
 *
 *
 *
 *
 *
 */
public class Main {
    public static void main(String[] args) {

        try {
            Application.launch(App.class, args);
        } catch (Exception error) {
            error.getStackTrace();
        }
    }

}
