import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Branch
{

    public static ArrayList<Branch> branches = new ArrayList<>();
    Point2D begin;
    Point2D end;
    Line node;
    double prevAng = 0;
    double angle = 0;
    double length = 0;
    Point2D r;
    Point2D l;
    Branch right;
    Branch left;

    public Branch(Point2D begin, Point2D end)
    {
        prevAng = 0;
        this.begin = begin;
        this.end = end;
        node = new Line(begin.getX(), begin.getY(), end.getX(), end.getY());
        node.setStrokeWidth(2.1);
        node.setStroke(Color.SNOW);
        angle = Utils.calculateAngle(begin, end);
        length = Utils.distance(begin, end);
        branches.add(this);
    }

    public Node getNode()
    {
        return this.node;
    }

    public void branch()
    {
        r = Utils.endPoint(end, this.angle - Main.angleConstant, this.length * 0.667);
        l = Utils.endPoint(end, this.angle + Main.angleConstant, this.length * 0.667);
        right = new Branch(this.end, r);
        left = new Branch(this.end, l);
        right.prevAng = this.angle;
        left.prevAng = this.angle;
    }

    public double getLength()
    {
        return this.length;
    }

    public void setBegin(Point2D begin)
    {
        this.begin = begin;
        this.node.setStartX(begin.getX());
        this.node.setStartY(begin.getY());
    }

    public void setEnd(Point2D end)
    {
        this.end = end;
        this.angle = Utils.calculateAngle(this.begin, this.end);
        this.node.setEndX(end.getX());
        this.node.setEndY(end.getY());
    }

    public void updateR(double updAngle)
    {
        if (right != null)
        {
            double ang = this.right.prevAng - updAngle;
            this.right.setBegin(this.end);
            Point2D endR = Utils.endPoint(this.end, ang, right.getLength());
            this.right.setEnd(endR);
            this.right.prevAng = this.angle;
        }
    }

    public void updateL(double updAngle)
    {
        if (left != null)
        {
            double ang = this.left.prevAng + updAngle;
            this.left.setBegin(this.end);
            Point2D endL = Utils.endPoint(this.end, ang, left.getLength());
            this.left.setEnd(endL);
            this.left.prevAng = this.angle;
        }
    }
}