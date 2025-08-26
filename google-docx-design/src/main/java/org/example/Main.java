package org.example;

import Document.Document;
import DocumentEditor.DocumentEditorHelper;
import Persistence.FileStorage;
import Persistence.IPersistence;

public class Main {
    public static void main(String[] args) {
        Document document=new Document();
        IPersistence storage=new FileStorage();
        DocumentEditorHelper documentEditor=new DocumentEditorHelper(document,storage);
        documentEditor.addTextELement("Privet, eto Sanjeeb!");
        documentEditor.addImageELement("/home/raisanj/Image.png");
        documentEditor.addTextELement("Spasibo za vizit");
        documentEditor.renderDocument();
        documentEditor.save();
    }
}
