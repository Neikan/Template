package ru.stqa.pft.sandbox;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class DistanceTest {
  @Test /*Проверка расстояния между точек с одинаковыми координатами*/
  public void testZeroZero() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 0);
    Assert.assertEquals(p1.distance(p2), 0.0);
  }

  @Test /*Проверка расстояния между началом координат и точкой на оси ординат*/
  public void testY() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(0, 3);
    Assert.assertEquals(p1.distance(p2), 3.0);
  }

  @Test /*Проверка расстояния между началом координат и точкой на оси абсцисс*/
  public void testX() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(99.999, 0);
    Assert.assertEquals(p1.distance(p2), 99.999);
  }

  @Test /*Проверка расстояния между началом координат и точкой с равными между собой ординатой и абсциссой*/
  public void testXYEquals() {
    Point p1 = new Point(0, 0);
    Point p2 = new Point(-1, -1);
    Assert.assertEquals(p1.distance(p2), 1.4142135623730951);
  }

  @Test /*Проверка расстояния между двумя произвольными точками*/
  public void testTwoDifferent() {
    Point p1 = new Point(-2, -1);
    Point p2 = new Point(3.2, 3);
    Assert.assertEquals(p1.distance(p2), 6.560487786742691);
  }

}
