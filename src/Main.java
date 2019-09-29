import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "JellyBeanci ";
    public static final int width = 1200;
    public static final int height = 800;
    private static Color backcolor = Color.rgb(51, 51, 51);
    private static Background background = new Background(new BackgroundFill(backcolor, new CornerRadii(0), new Insets(0, 0, 0, 0)));
    private static Timeline update;
    private static int factor = 10;
    public static double angleConstant = 30;
    //
    private static boolean perlin = false;
    private static double xoff = 10;
    private static double xdif = 0.005;
    //
    public static boolean colorMode = false;

    @Override
    public void start(Stage stage) throws Exception
    {
        BorderPane root = new BorderPane();
        Pane pane = new Pane();
        child = pane.getChildren();
        pane.setBackground(background);
        Slider sl = new Slider(-90, 0, 30);
        root.setCenter(pane);
        root.setPadding(new Insets(10, 10, 5, 10));
        root.setBottom(sl);
        sl.setOnMouseDragged(e -> {
            //stage.setTitle(Double.toString(sl.getValue()));
            if (!perlin)
            {
                for (int i = 0; i < Branch.branches.size(); i++)
                {
                    Branch temp = Branch.branches.get(i);
                    temp.updateR(sl.getValue());
                    temp.updateL(sl.getValue());
                }
            }
        });
        //        root.setOnDragDetected(e->root.startFullDrag());

        //
        //DRAW
        double initLength = 250;
        Point2D a = new Point2D(width / 2, height);
        Point2D b = new Point2D(width / 2, height - initLength);
        Branch rot = new Branch(a, b);
        int limit = (int) Math.pow(2, factor) - 1;
        for (int i = 0; i < limit; i++)
        {
            Branch.branches.get(i).branch();
        }
        for (Branch branch : Branch.branches)
        {
            child.add(branch.getNode());
        }
        //
        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //PLAY
                    update.play();
                    perlin = true;
                    stage.setTitle(title + "Perlin Noise Mode Active F2 to Manual mode");
                    break;
                }
                case F2:
                {
                    //PAUSE
                    update.pause();
                    perlin = false;
                    stage.setTitle(title + "Manuel Mode Active F1 to Perlin Noise mode");
                    break;
                }
                case F3:
                {
                    //Show Child Count
                    System.out.println("Child Count: " + child.size());
                    break;
                }
                case F5:
                {
                    colorMode = !colorMode;
                    break;
                }
            }
        });
        update = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            //60 fps
            double noise = PerlinNoise.noise(xoff, 0, -90, 0, true);
            xoff += xdif;
            for (int i = 0; i < Branch.branches.size(); i++)
            {
                Branch temp = Branch.branches.get(i);
                temp.updateR(noise);
                temp.updateL(noise);
            }
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(1);
        update.setAutoReverse(false);
        //update.play(); //uncomment for play when start
        //
        stage.setTitle(title + "Manuel Mode Active F1 to Perlin Noise mode");
        stage.setResizable(false);
        stage.setScene(new Scene(root, width - 10, height - 10, backcolor));
        stage.show();
        root.requestFocus();
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
