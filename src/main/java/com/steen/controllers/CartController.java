package com.steen.controllers;

import com.steen.models.Model;

import java.util.HashMap;

public class CartController {
    private HashMap<String, Model> models;

    public CartController(HashMap<String, Model> models) {
        this.models = models;
    }
}
