package DependencyInversionPrinciple;

interface Keyboard {
    void type();
}

interface Mouse {
    void click();
}

class WiredKeyboard implements Keyboard {
    @Override
    public void type() {
        System.out.println("Typing with wired keyboard...");
    }
}

class WirelessKeyboard implements Keyboard {
    @Override
    public void type() {
        System.out.println("Typing with wireless keyboard...");
    }
}

class WiredMouse implements Mouse {
    @Override
    public void click() {
        System.out.println("Clicking with wired mouse...");
    }
}

class WirelessMouse implements Mouse {
    @Override
    public void click() {
        System.out.println("Clicking with wireless mouse...");
    }
}

class Computer {
    private final Keyboard keyboard;
    private final Mouse mouse;

    // Constructor injection (DIP applied)
    public Computer(Keyboard keyboard, Mouse mouse) {
        this.keyboard = keyboard;
        this.mouse = mouse;
    }

    public void operate() {
        keyboard.type();
        mouse.click();
    }
}

public class DependencyInversionPrinciple {
    public static void main(String[] args) {
        Keyboard keyboard = new WiredKeyboard(); // can switch to WirelessKeyboard
        Mouse mouse = new WirelessMouse();  // can switch to WiredMouse

        Computer computer = new Computer(keyboard, mouse);
        computer.operate();
    }
}
