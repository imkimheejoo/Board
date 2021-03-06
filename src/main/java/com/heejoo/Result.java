package com.heejoo;

public class Result {
    private boolean valid;
    private String errorMessage;

    public Result(){
        this(true,null);
    }
    public Result(boolean valid,String errorMessage) {
        this.valid=valid;
        this.errorMessage=errorMessage;
    }

    public boolean isValid() {
        return valid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public static Result ok() {
        return  new Result();
    }
    public static Result fail(String errorMessage){
        return new Result(false,errorMessage);
    }

}
