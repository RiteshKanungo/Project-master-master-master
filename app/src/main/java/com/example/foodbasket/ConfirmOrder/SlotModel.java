package com.example.foodbasket.ConfirmOrder;

public class SlotModel {
    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }

    String slots,readable;

    public SlotModel(String slots, String readable) {
        this.slots = slots;
        this.readable = readable;
    }

    public String getSlots() {
        return slots;
    }

    public void setSlots(String slots) {
        this.slots = slots;
    }


}
