package Document;

import DocumentElement.IDocumentElement;

import java.util.ArrayList;
import java.util.List;

public class Document {
    List<IDocumentElement>elements = new ArrayList<>();

    public void addElement(IDocumentElement element){
        elements.add(element);
    }

    public String renderDocument(){
        StringBuilder renderedDocument=new StringBuilder();
        for(IDocumentElement element:elements){
           String content = element.render();
           if (content == null) {
               continue;
           }
           String withoutTrailingNewlines = content.replaceAll("[\\r\\n]+$", "");
           renderedDocument.append(withoutTrailingNewlines).append(System.lineSeparator());
        }
        return renderedDocument.toString();
    }
}
