package ru.boshchenko;

import ru.boshchenko.geometryLibrary.base.twoDimensionalShapes.*;

import java.util.List;

public class GeometryApp {
    public static void main(String[] args) {

        Triangle triangle = new Triangle(5.0, 3.0, 6.0);
        Circle circle = new Circle(6.25);
        Square square = new Square(7.5);
        Rectangle rectangle = new Rectangle(3.5, 8.4);

        List<TwoShape> twoShapes = List.of(triangle, circle, square, rectangle);

        twoShapes.forEach(shape -> System.out.printf("Фигура: %s\nПериметр = %f\nПлощадь = %f\n\n"
                , shape, shape.calculatePerimeter(), shape.calculateArea()));

        System.out.println(triangle.getLengthFirstSide() + " " + triangle.getLengthSecondSide() + " " + triangle.getLengthThirdSide());
        System.out.println(circle.getRadius());
        System.out.println(square.getLengthSide());
        System.out.println(rectangle.getLength() + " " + rectangle.getWidth());

    }
}
