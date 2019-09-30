import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.util.ArrayList;

public class Branch
{

    public static ArrayList<Branch> branches = new ArrayList<>();
    private Point2D begin;
    private Point2D end;
    private Line node;
    private double prevAng = 0;
    private double angle = 0;
    private double length = 0;
    private Point2D r;
    private Point2D l;
    private Branch right;
    private Branch left;
    private double xoff = 12;
    public static double yoff = 1;
    private boolean done = false;
    private double thickness = 6;

    public Branch(Point2D begin, Point2D end)
    {
        prevAng = 0;
        this.begin = begin;
        this.end = end;
        node = new Line(begin.getX(), begin.getY(), end.getX(), end.getY());
        //node.setStrokeWidth(2.1);
        node.setStrokeWidth(this.thickness);
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
        right.xoff = this.xoff += 0.1;
        left.xoff = this.xoff += 0.1;
        right.setThickness(this.thickness * 0.8);
        left.setThickness(this.thickness * 0.8);
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

    public void setThickness(double thickness)
    {
        this.thickness = thickness;
        this.node.setStrokeWidth(thickness);
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
        //this.xoff += 0.001;
        if (Main.colorMode)
        {
            double noise = PerlinNoise.noise(xoff, yoff, 0, 360, true);
            this.node.setStroke(Color.hsb(noise, 1, 1));
            done = false;
        } else
        {
            if (!done)
            {
                this.node.setStroke(Color.SNOW);
                done = true;
            }
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
        //this.xoff += 0.001;
        if (Main.colorMode)
        {
            double noise = PerlinNoise.noise(xoff, yoff, 0, 360, true);
            this.node.setStroke(Color.hsb(noise, 1, 1));
            done = false;
        } else
        {
            if (!done)
            {
                this.node.setStroke(Color.SNOW);
                done = true;
            }
        }
    }
}