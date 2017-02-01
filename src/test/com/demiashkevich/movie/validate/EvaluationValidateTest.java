package com.demiashkevich.movie.validate;

import com.demiashkevich.movie.entity.Evaluation;
import com.demiashkevich.movie.validation.EvaluationValidation;
import com.demiashkevich.movie.validation.Validation;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class EvaluationValidateTest {


    private boolean expected;
    private String title;
    private String comment;
    private double rating;

    public EvaluationValidateTest(boolean expected, String title, String comment, double rating) {
        this.expected = expected;
        this.title = title;
        this.comment = comment;
        this.rating = rating;
    }

    @Test
    public void checkCreateVegetables(){
        //Initialize evaluation object
        Evaluation evaluation = new Evaluation();
        evaluation.setTitle(title);
        evaluation.setComment(comment);
        evaluation.setRating(rating);

        Validation<Evaluation> validation = new EvaluationValidation();

        boolean actual = validation.execute(evaluation);
        Assert.assertSame(expected, actual);
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {true, "Hello", "World", 8},
                {true, "Madagascar 3", "World", 10},
                {false, "Madagascar 3", "World", 0},
                {false, "Madagascar 3", "World", 20},
        });
    }

}
