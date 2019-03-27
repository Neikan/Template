package ru.stqa.pft.sandbox;

public class Point {

  public double x;
  public double y;

  public Point(double x, double y){
    this.x = x;
    this.y = y;
  }

  /*Выполнение задания №2*/
  public double distance(Point p2){
    return Math.sqrt((p2.x - this.x) * (p2.x - this.x) + (p2.y - this.y) * (p2.y - this.y)); /*Вычисляется по формуле: корень квадратный из суммы квадратов разностей соответствующих координат второй и первой точек*/
  }

}
