package com.demo.Stream.controller;

import java.util.List;

public class StreamResponse {
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

    private String object;
    private String model;
    private List<Choice> choices;

    // Getters and Setters

    public static class Choice {
        private int index;
        private Delta delta;

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }

        public Delta getDelta() {
            return delta;
        }

        public void setDelta(Delta delta) {
            this.delta = delta;
        }

        // Getters and Setters
        public static class Delta {
            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            private String content;

            // Getters and Setters
        }
    }
}
