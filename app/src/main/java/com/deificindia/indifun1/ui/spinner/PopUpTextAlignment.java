package com.deificindia.indifun1.ui.spinner;

enum PopUpTextAlignment {
    START(0),
    END(1),
    CENTER(2);

    private final int id;

    PopUpTextAlignment(int id) {
        this.id = id;
    }

    static PopUpTextAlignment fromId(int id) {
        for (PopUpTextAlignment value : values()) {
            if (value.id == id) return value;
        }
        return CENTER;
    }
}