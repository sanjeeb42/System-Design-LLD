package DocumentElement;

public class TextElement implements IDocumentElement {
    private String text="";

    public TextElement(String data){
        this.text=data;
    }
    @Override
    public String render() {
        return text;
    }
}
