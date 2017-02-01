package com.demiashkevich.movie.validation;

import com.demiashkevich.movie.entity.Evaluation;

public class EvaluationValidation implements Validation<Evaluation> {

    private static final byte MIN_MARK = 1;
    private static final byte MAX_MARK = 10;

    @Override
    public boolean execute(Evaluation evaluation) {
        double rating = evaluation.getRating();
        String title = evaluation.getTitle();
        String comment = evaluation.getComment();
        if(title == null || title.isEmpty()){
            return false;
        }
        if(comment == null || comment.isEmpty()){
            return false;
        }
        if(!this.ratingValidate(rating)){
            return false;
        }
        this.replaceCharacter(comment);
        return true;
    }

    private boolean ratingValidate(double rating){
        return rating >= MIN_MARK && rating <= MAX_MARK;
    }

    private String replaceCharacter(String comment){
        return comment.replace('<', '|');
    }

}
