package ru.stqa.pft.sandbox;

public class MyFirstProgram {

	public static void main(String[] args) {
	/*Лекции*/
		hello("world");
		hello("user");
		hello("Eugene");

		Square s = new Square(5);
		System.out.println("Площадь квадрата со стороной " + s.l + " = " + s.area());

		Rectangle r = new Rectangle(4, 6);
		System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b + " = " + r.area());

	/*Выполнение задания №2. Пункты 1-3*/
		/*
		Point p1 = new Point();
		p1.x = 1;
		p1.y = 1;

		Point p2 = new Point();
		p2.x = 2;
		p2.y = 2;

		System.out.println("Расстояние между точками c координатами (" + p1.x + ";" + p1.y + ") и (" + p2.x + ";" + p2.y + ") = " + distance(p1, p2));
		*/

		/*Выполнение задания №2*. Пункт 4 */
		Point p1 = new Point(-2.0,2);
		Point p2 = new Point(1, 1);

		System.out.println("Расстояние между точками c координатами (" + p1.x + ";" + p1.y + ") и (" + p2.x + ";" + p2.y + ") = " + p1.distance(p2));

	}

	/*Лекции*/
	public static void hello(String somebody) {
		System.out.println("Hello, " + somebody + "!");
	}

	/*Выполнение задания №2*. Пункты 1-3/
	/*public static double distance(Point p1, Point p2){
		return Math.sqrt((p2.x - p1.x) * (p2.x - p1.x) + (p2.y - p1.y) * (p2.y - p1.y)); /*Вычисляется по формуле: корень квадратный из суммы квадратов разностей соответствующих координат второй и первой точек*/

}