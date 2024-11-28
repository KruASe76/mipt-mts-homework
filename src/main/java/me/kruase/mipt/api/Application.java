package me.kruase.mipt.api;


import me.kruase.mipt.api.controllers.Controller;

import java.util.List;


public final class Application {
    private final List<Controller> controllers;

    public Application(List<Controller> controllers) {
        this.controllers = controllers;
    }

    public void start() {
        controllers.forEach(Controller::initializeEndpoints);
    }
}
