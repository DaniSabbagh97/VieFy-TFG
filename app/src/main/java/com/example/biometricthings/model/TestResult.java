package com.example.biometricthings.model;

public class TestResult {

    private Test t;
    private User u;

    public TestResult(Test t, User u) {
        this.t = t;
        this.u = u;
    }

    public Test getT() {
        return t;
    }

    public void setT(Test t) {
        this.t = t;
    }

    public User getU() {
        return u;
    }

    public void setU(User u) {
        this.u = u;
    }
}
