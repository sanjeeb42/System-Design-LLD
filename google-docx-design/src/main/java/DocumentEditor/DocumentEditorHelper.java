package DocumentEditor;

import Document.Document;
import DocumentElement.ImageElement;
import DocumentElement.TextElement;
import Persistence.IPersistence;

public class DocumentEditorHelper {
    public Document document;
    public IPersistence persistence;
    private String renderedDocument="";

    public DocumentEditorHelper(Document document,IPersistence persistence){
        this.document=document;
        this.persistence=persistence;
    }

    public void addTextELement(String text){
        document.addElement(new TextElement(text));
    }

    public void addImageELement(String image){
        document.addElement(new ImageElement(image));
    }

    public String renderDocument(){
        if(renderedDocument.isEmpty()){
            renderedDocument=document.renderDocument();
        }
        return renderedDocument;
    }

    public void save(){
        persistence.save(renderedDocument);
    }
}
