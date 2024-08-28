package com.ericsson.cds.uisdk.compositecomponents.table;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ericsson.cifwk.taf.ui.core.UiComponent;

public class TableHelper {

    /**
     * Gets the text content of elements by tag name.
     *
     * @param uiComponent the ui component
     * @param tagSelectorOfElements the tag selector of elements
     * @return the text content of elements by tag name
     */
    public static List<String> getTextContentOfElementsByTagName(UiComponent uiComponent,
            String tagSelectorOfElements) {
        return getTextContentOfElements(uiComponent, tagSelectorOfElements, false);
    }

    /**
     * Gets the text content of table.
     *
     * @param table the table
     * @param tableRowClassSelector the table row class selector
     * @return the text content of table
     */
    public static List<List<String>> getTextContentOfTable(UiComponent table, String tableRowClassSelector) {

        final String getTableCellsJavascript = "var tableRows = element.getElementsByClassName('"
                + tableRowClassSelector + "');\n" + "tableData = [];\n"
                + "for (tableRowIndex = 0; tableRowIndex < tableRows.length; ++tableRowIndex) {\n"
                + "    var rowCells = tableRows[tableRowIndex].children;\n" + "    var rowCellsInString = [];\n"
                + "    for (index = 0; index < rowCells.length; ++index) {\n"
                + "        rowCellsInString[index] = rowCells[index].textContent.trim();\n" + "    }\n"
                + "    tableData[tableRowIndex] = rowCellsInString;\n" + "}\n" + "return tableData;";

        @SuppressWarnings("unchecked")
        List<List<String>> tableData = (List<List<String>>) table.evaluate(getTableCellsJavascript);
        if (tableData == null) {
            return new ArrayList<>();
        }
        return tableData;
    }

    /**
     * Method checks if Hash map contains key(ignore case) / value.
     *
     * @param hashMap the hash map
     * @param keyIgnoreCase the key ignore case
     * @param value the value
     * @return true, if successful
     */
    public static boolean hashMapContainsKeyValueIgnoreKeyCase(HashMap<String, String> hashMap, String keyIgnoreCase,
            String value) {
        for (String currentKey : hashMap.keySet()) {
            if (currentKey.equalsIgnoreCase(keyIgnoreCase))
                if (hashMap.get(currentKey).equals(value))
                    return true;
        }

        return false;
    }

    /**
     * Convert all Strings in list to lowercase.
     *
     * @param list the list
     * @return the list
     */
    public static List<String> listToLowercase(List<String> list) {
        List<String> newList = new ArrayList<>();
        for (String string : list) {
            newList.add(string.toLowerCase());
        }
        return newList;
    }

    /**
     * Gets the text content of elements.
     * 
     * @param uiComponent the ui component
     * @param classSelectorOfElements the class selector of elements
     * @param isClassSelector the is class selector
     * @return the text content of elements
     */
    private static List<String> getTextContentOfElements(UiComponent uiComponent, String classSelectorOfElements,
            boolean isClassSelector) {

        // remove first dot from selector if exist (e.g. .ebTabs-tabItem -> ebTabs-tabItem)
        if (isClassSelector && classSelectorOfElements.startsWith(".")) {
            classSelectorOfElements = classSelectorOfElements.substring(1);
        }

        final String getElementBy = isClassSelector ? "getElementsByClassName" : "getElementsByTagName";

        final String getTextJavaScript = "var items = element." + getElementBy + "(\"" + classSelectorOfElements
                + "\");\n" + "var itemsInString = [];\n" + "for (index = 0; index < items.length; ++index) {\n"
                + "    itemsInString[index] = items[index].textContent.trim();\n" + "}\n" + "return itemsInString;";

        @SuppressWarnings("unchecked")
        List<String> text = (List<String>) uiComponent.evaluate(getTextJavaScript);
        return text;
    }

}
