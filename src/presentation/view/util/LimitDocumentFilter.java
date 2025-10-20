/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentation.view.util;

// Importaciones necesarias
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet; // <-- Agrega esta
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
/**
 *
 * @author Scott.perez
 */
public class LimitDocumentFilter extends DocumentFilter {
    
    // Aquí debe ir el código del constructor y los métodos insertString/replace
    private int limite;

    public LimitDocumentFilter(int limite) {
        if (limite <= 0) {
            throw new IllegalArgumentException("El límite debe ser mayor que cero.");
        }
        this.limite = limite;
    }
    @Override
    public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
        if (string == null) return;
        if ((fb.getDocument().getLength() + string.length()) <= limite) {
            super.insertString(fb, offset, string, attr);
        }
    }
    @Override
    public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
        if (text == null) return;
        if ((fb.getDocument().getLength() - length + text.length()) <= limite) {
            super.replace(fb, offset, length, text, attrs);
        }
    }
}