package ru.boshchenko;

import ru.boshchenko.geometryLibrary.base.twoDimensionalShapes.*;

import java.util.List;

public class GeometryApp {
    public static void main(String[] args) {

        TwoShape twoShape = new Triangle(5.0, 3.0, 6.0);
        TwoShape twoShape1 = new Circle(6.25);
        TwoShape twoShape2 = new Square(7.5);
        TwoShape twoShape3 = new Rectangle(3.5, 8.4);

        List<TwoShape> twoShapes = List.of(twoShape, twoShape1, twoShape2, twoShape3);

        twoShapes.forEach(shape -> System.out.printf("Фигура: %s\nПериметр = %f\nПлощадь = %f\n\n"
                , shape, shape.calculatePerimeter(), shape.calculateArea()));

    }
}
