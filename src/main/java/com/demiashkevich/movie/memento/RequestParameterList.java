package com.demiashkevich.movie.memento;

import java.util.ArrayDeque;

public class RequestParameterList {

    private static final byte SIZE = 2;

    private ArrayDeque<RequestParameter> requestParameters = new ArrayDeque<>(SIZE);

    public RequestParameterList(){}

    public void offerLast(RequestParameter requestParameter) {
        if(requestParameters.size() > SIZE){
            requestParameters.removeFirst();
            requestParameters.offerLast(requestParameter);
        } else {
            requestParameters.offerLast(requestParameter);
        }
    }

    public RequestParameter pollLast() {
        return requestParameters.pollLast();
    }

}
