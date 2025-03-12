package ru.boshchenko;

import geometryLibrary.base.twoDimensionalShapes.*;
import geometryUtils.base.ComparingShapes;
import geometryUtils.base.MetricSystem;
import threeDimensionalShapes.Cube;
import threeDimensionalShapes.Sphere;

import java.util.List;

public class GeometryApp {
    public static void main(String[] args) {

        Triangle triangle = new Triangle(50, 30, 60);
        Circle circle = new Circle(625);
        Square square = new Square(75);
        Rectangle rectangle = new Rectangle(35, 84);

        List<TwoShape> twoShapes = List.of(triangle, circle, square, rectangle);

        twoShapes.forEach(shape -> System.out.printf("Фигура: %s\nПериметр = %d\nПлощадь = %d\n\n"
                , shape, shape.calculatePerimeter(), shape.calculateArea()));

        System.out.println(triangle.getLengthFirstSide() + " " + triangle.getLengthSecondSide() + " " + triangle.getLengthThirdSide());
        System.out.println(circle.getRadius());
        System.out.println(square.getLengthSide());
        System.out.println(rectangle.getLength() + " " + rectangle.getWidth());

        System.out.println("--------------------------------Площадь круга в дргуих метрических значенях -------------------------------------");
        System.out.println(circle.calculateArea() + " миллиметров");
        System.out.println(MetricSystem.MILLIMETER.toCentimeters(circle.calculateArea()) + " сантиметров");
        System.out.println(MetricSystem.MILLIMETER.toDecimeters(circle.calculateArea()) + " дециметров");
        System.out.println(MetricSystem.MILLIMETER.toMeters(circle.calculateArea()) + " метров");
        System.out.println(MetricSystem.MILLIMETER.toKilometers(circle.calculateArea()) + " километров");

        ComparingShapes<TwoShape> comparingShapes;

        Cube cube = new Cube(5);
        Sphere sphere = new Sphere(3);
        System.out.printf("Трехмерная фигура: %s, площадь фигуры: %d, объем фигуры %d\n", cube, cube.calculateArea(), cube.calculateVolume());
        System.out.printf("Трехмерная фигура: %s, площадь фигуры: %d, объем фигуры %d\n", sphere, sphere.calculateArea(), sphere.calculateVolume());

    }
}
