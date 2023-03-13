package Pojo;

import java.util.List;

public class CreateOrderJson {
    private String[] color;

    public CreateOrderJson(List<String> color) {
        this.color = (String[]) color.toArray();
    }

    public String[] getColor() {
        return color;
    }

    public void setColor(String[] color) {
        this.color = color;
    }
}
