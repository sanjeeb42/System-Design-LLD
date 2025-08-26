package DocumentElement;

public class ImageElement implements IDocumentElement {

    private String image="";
    public ImageElement (String data){
        this.image=data;
    }
    @Override
    public String render() {
       return "Image is: "+image;
    }
}
