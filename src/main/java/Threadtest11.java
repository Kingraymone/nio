
import connector.utils.annotation.*;


@ClassAnnotation1(interests = {"I1","I2"})
@ClassAnnotation2(phase = "social")
public class Threadtest11  {

    @FieldAnnotaion2(height = 100) @FieldAnnotaion1() String name;

    @MethodAnnotation1(food = "Meat")
    @MethodAnnotation2(position = "Restaurant")
    public void eat(@ParameterAnnotation2() @ParameterAnnotation1(tag = "String") String name){

    }

    @MethodAnnotation1(food = "Null")
    @MethodAnnotation2(time=10,position = "Home")
    public void sleep(){
        @FieldAnnotaion1() String sex="field";
        @FieldAnnotaion2(height = 20) String name = "king";

    }

}

