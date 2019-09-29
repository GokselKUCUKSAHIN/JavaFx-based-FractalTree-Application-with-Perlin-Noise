import javafx.geometry.Point2D;

public class BranchMine
{

    double angle = 0;
    double length = 0;
    Point2D start = new Point2D(0, 0);
    Point2D end = new Point2D(0, 0);
    Point2D r;
    Point2D l;
    public BranchMine(Point2D start, double length, double angle)
    {
        this.angle = angle;
        this.length = length;
        this.start = start;
        end = Utils.endPoint(start.getX(), start.getY(), angle, length);
    }

    public BranchMine(double startX, double startY, double length, double angle)
    {
        this(new Point2D(startX, startY), length, angle);
    }

}
