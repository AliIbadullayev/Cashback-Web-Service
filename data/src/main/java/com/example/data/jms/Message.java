package com.example.data.jms;

import java.io.Serializable;

public record Message(String name, String email, long id) implements Serializable {
}
