package com.demiashkevich.movie.memento;

import java.util.ArrayDeque;

public class RequestParameterList {

    private static final byte SIZE = 5;

    private static ArrayDeque<RequestParameter> requestParameters = new ArrayDeque<>(SIZE);

    private RequestParameterList(){}

    public static RequestParameterList getInstance(){
        return new RequestParameterList();
    }

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

    public static void setRequestParameters(ArrayDeque<RequestParameter> requestParameters) {
        RequestParameterList.requestParameters = requestParameters;
    }
}
