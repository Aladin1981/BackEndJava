package LessonAPI.patterns.fabric;

public class WinButtonFabric implements ButtonFabric {
    @Override
    public Button createButton() {
        return new WinButton();
    }
}
